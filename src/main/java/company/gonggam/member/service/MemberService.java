package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam._core.jwt.JWTTokenProvider;
import company.gonggam._core.utils.RedisUtils;
import company.gonggam.member.domain.AgeGroup;
import company.gonggam.member.domain.Gender;
import company.gonggam.member.domain.Member;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.repository.MemberRepository;
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
    public void verifyEmail(String email, String userCode) {

        // redis에서 code 확인
        String code = redisUtils.getHashValue(EMAIL_PREFIX + email, "code");

        // 인증번호 확인
        if(!Objects.equals(code, userCode)) {
            throw new ApplicationException(ErrorCode.INVALID_EMAIL_CODE);
        }

        // 해당 code에 대해 <email, 유형, 인증여부> 저장
        redisUtils.setEmailKey(EMAIL_PREFIX + email, "verify", VALIDATED_EMAIL_STATUS, EMAIL_VALIDATATION_TIME, TimeUnit.DAYS);
    }

    /*
        기본 로그인
     */
    public MemberResponseDTO.authTokenDTO login(MemberRequestDTO.loginDTO requestDTO) {

        // 회원 확인

        // 1. 이메일 확인
        Member member = findMemberByEmail(requestDTO.email())
                .orElseThrow(() -> new ApplicationException(ErrorCode.SAME_EMAIL));

        // 2. 비밀번호 확인
        checkValidPassword(requestDTO.password(), member.getPassword());

        // 토큰 발급
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());
        AuthenticationManager manager = authenticationManagerBuilder.getObject();
        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    // 비밀번호 확인
    private void checkValidPassword(String rawPassword, String encodedPassword) {

        log.info(rawPassword + " " + encodedPassword);

        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
    }

    protected Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 회원 생성
    protected Member newMember(MemberRequestDTO.signUpDTO requestDTO) {
        return Member.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .gender(Gender.fromString(requestDTO.gender()))
                .ageGroup(AgeGroup.fromInt(requestDTO.age()))
                .build();
    }
}
