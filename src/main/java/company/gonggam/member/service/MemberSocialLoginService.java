package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam._core.jwt.JWTTokenProvider;
import company.gonggam._core.utils.RedisUtils;
import company.gonggam.member.domain.*;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.property.KakaoProperties;
import company.gonggam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberSocialLoginService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtils redisUtils;
    private final JWTTokenProvider jwtTokenProvider;

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoProperties kakaoProperties;

    /*
        카카오 로그인
     */
    // 카카오로부터 받은 최신 사용자 정보로 데이터베이스 내의 사용자 정보를 갱신할 필요가 있을까?
    @Transactional
    public MemberResponseDTO.authTokenDTO kakaoLogin(String code) {

        // 토큰 발급
        String accessToken = generateAccessToken(code);

        // 사용자 정보
        MemberResponseDTO.KakaoInfoDTO profile = getKakaoProfile(accessToken);

        // 회원 확인
        Member member = memberService.findMemberByEmail(profile.kakaoAccount().email())
                .orElseGet(() -> kakaoSignUp(profile));

        return getSocialAuthTokenDTO(member);
    }

    private String generateAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", kakaoProperties.getGrantType());
        params.add("client_id", kakaoProperties.getClientId());
        params.add("redirect_uri", kakaoProperties.getRedirectUri());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<MemberResponseDTO.KakaoTokenDTO> response = restTemplate.postForEntity(
                kakaoProperties.getTokenUri(),
                httpEntity,
                MemberResponseDTO.KakaoTokenDTO.class
        );

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new ApplicationException(ErrorCode.FAILED_GET_ACCESS_TOKEN);
        }

        return response.getBody().accessToken();
    }

    private MemberResponseDTO.KakaoInfoDTO getKakaoProfile(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        ResponseEntity<MemberResponseDTO.KakaoInfoDTO> response = restTemplate.postForEntity(
                kakaoProperties.getUserInfoUri(),
                new HttpEntity<>(headers),
                MemberResponseDTO.KakaoInfoDTO.class
        );

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new ApplicationException(ErrorCode.FAILED_GET_KAKAO_PROFILE);
        }

        return response.getBody();
    }

    protected Member kakaoSignUp(MemberResponseDTO.KakaoInfoDTO profile) {
        log.info("카카오 회원 생성 : " + profile.kakaoAccount().email());

        Member member = Member.builder()
                .name(profile.properties().nickname())
                .email(profile.kakaoAccount().email())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .gender(Gender.fromString(profile.kakaoAccount().gender()))
                .ageGroup(AgeGroup.fromString(profile.kakaoAccount().age_range()))
                .socialType(SocialType.KAKAO)
                .authority(Authority.USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    /*
        네이버 로그인
     */
    public MemberResponseDTO.authTokenDTO naverLogin(String code) {

        // 회원 확인

        // 토큰 발급

        //return new MemberResponseDTO.authTokenDTO();
        return null;
    }

    private MemberResponseDTO.authTokenDTO getSocialAuthTokenDTO(Member member) {
        UserDetails userDetails = new User(member.getEmail(), "",
                Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority().toString())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return jwtTokenProvider.generateToken(authentication);
    }
}
