package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam._core.jwt.JWTTokenProvider;
import company.gonggam._core.utils.ClientUtils;
import company.gonggam._core.utils.RedisUtils;
import company.gonggam.member.domain.*;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.repository.MemberRepository;
import company.gonggam.redis.domain.RefreshToken;
import company.gonggam.redis.repository.RefreshTokenRedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtils redisUtils;
    private final JWTTokenProvider jwtTokenProvider;

    private final String EMAIL_PREFIX = "email:";
    private final long EMAIL_VALIDATATION_TIME = 7L;
    private final String VALIDATED_EMAIL_STATUS = "true";

    /*
        기본 회원 가입
     */
    @Transactional
    public void signUp(MemberRequestDTO.signUpDTO requestDTO) {

        // TODO: 예외 처리
        // 이메일 중복
        checkDuplicatedEmail(requestDTO.email());

        // 비밀번호 확인
        checkValidPassword(requestDTO.password(), passwordEncoder.encode(requestDTO.confirmPassword()));

        // 이메일 인증 : 해당 email에 대한 인증여부 redis에서 확인
        checkValidEmail(requestDTO.email());

        // 회원 생성
        Member member = newMember(requestDTO);

        // 회원 저장
        memberRepository.save(member);
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            throw new ApplicationException(ErrorCode.SAME_EMAIL);
        }
    }

    private void checkValidEmail(String email) {

        String result = redisUtils.getHashValue(EMAIL_PREFIX + email, "verify");

        if (!VALIDATED_EMAIL_STATUS.equals(result)) {
            throw new ApplicationException(ErrorCode.INVALID_EMAIL);
        }
    }

    // 이메일 인증번호 전송
    public void checkEmailCode(String email) {

        // 앞선 depth에서 인증을 한다면 중복 확인도 먼저 해주는게 좋지 않나?
        // 이메일 인증 시 버튼 클릭을 한다고 치면 중복된 이메일 체크도 같이 해주는 것이 좋을 듯
        checkDuplicatedEmail(email);

        // emailService로 인증번호 전송
        emailService.sendEmailCode(email);
}

    // 이메일 인증번호 확인
    public void certifyEmail(MemberRequestDTO.certifyEmailDTO requestDTO) {

        String email = requestDTO.email();
        String userCode = requestDTO.code();

        // redis에서 code 확인
        String code = redisUtils.getHashValue(EMAIL_PREFIX + email, "code");

        log.info("해당 이메일의 인증 코드 : " + code);

        // 인증번호 확인
        // code가 null일 경우 NullPointerException
        if(!Objects.equals(code, userCode)) {
            throw new ApplicationException(ErrorCode.INVALID_EMAIL_CODE);
        }

        // 해당 code에 대해 <email, 유형, 인증여부> 저장
        redisUtils.setEmailKey(EMAIL_PREFIX + email, "verify", VALIDATED_EMAIL_STATUS, EMAIL_VALIDATATION_TIME, TimeUnit.DAYS);
    }

    /*
        기본 로그인
     */
    public MemberResponseDTO.authTokenDTO login(HttpServletRequest httpServletRequest, MemberRequestDTO.loginDTO requestDTO) {

        // 1. 이메일 확인
        Member member = findMemberByEmail(requestDTO.email())
                .orElseThrow(() -> new ApplicationException(ErrorCode.EMPTY_EMAIL_MEMBER));

        // 2. 비밀번호 확인
        checkValidPassword(requestDTO.password(), member.getPassword());

        return getAuthTokenDTO(requestDTO.email(), requestDTO.password(), httpServletRequest);
    }

    // 비밀번호 확인
    private void checkValidPassword(String rawPassword, String encodedPassword) {

        log.info(rawPassword + " " + encodedPassword);

        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
    }

    protected Optional<Member> findMemberByEmail(String email) {
        log.info("회원 확인 : " + email);

        return memberRepository.findByEmail(email);
    }

    // 회원 생성
    protected Member newMember(MemberRequestDTO.signUpDTO requestDTO) {
        return Member.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .gender(Gender.fromString(requestDTO.gender()))
                .ageGroup(AgeGroup.fromString(requestDTO.age_range()))
                .socialType(SocialType.NONE)
                .authority(Authority.USER)
                .build();
    }

    // 토큰 발급
    protected MemberResponseDTO.authTokenDTO getAuthTokenDTO(String email, String password, HttpServletRequest httpServletRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);
        AuthenticationManager manager = authenticationManagerBuilder.getObject();
        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);

        MemberResponseDTO.authTokenDTO authTokenDTO = jwtTokenProvider.generateToken(authentication);

        refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(authentication.getName())
                .ip(ClientUtils.getClientIp(httpServletRequest))
                .authorities(authentication.getAuthorities())
                .refreshToken(authTokenDTO.refreshToken())
                .build()
        );

        return authTokenDTO;
    }

    // 토큰 재발급
    public MemberResponseDTO.authTokenDTO reissueToken(HttpServletRequest httpServletRequest) {

        // Request Header 에서 JWT Token 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        // 토큰 유효성 검사
        if(token == null || !jwtTokenProvider.validateToken(token)) {
            throw new ApplicationException(ErrorCode.FAILED_VALIDATE_TOKEN);
        }

        // type 확인
        if(!jwtTokenProvider.isRefreshToken(token)) {
            throw new ApplicationException(ErrorCode.IS_NOT_REFRESH_TOKEN);
        }

        // RefreshToken
        RefreshToken refreshToken = refreshTokenRedisRepository.findByRefreshToken(token);

        if(refreshToken == null) {
            throw new ApplicationException(ErrorCode.FAILED_GET_RERFRESH_TOKEN);
        }

        // 최초 로그인한 ip와 같은지 확인
        String currentIp = ClientUtils.getClientIp(httpServletRequest);
        if(!currentIp.equals(refreshToken.getIp())) {
            throw new ApplicationException(ErrorCode.DIFFERENT_IP_ADDRESS);
        }

        // Redis 에 저장된 RefreshToken 정보를 기반으로 JWT Token 생성
        MemberResponseDTO.authTokenDTO authTokenDTO = jwtTokenProvider.generateToken(
                refreshToken.getId(), refreshToken.getAuthorities()
        );

        // Redis 에 RefreshToken Update
        refreshTokenRedisRepository.save(RefreshToken.builder()
                        .id(refreshToken.getId())
                        .ip("")
                        .authorities(refreshToken.getAuthorities())
                        .refreshToken(authTokenDTO.refreshToken())
                .build());

        return authTokenDTO;
    }
}
