package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.member.domain.AgeGroup;
import company.gonggam.member.domain.Gender;
import company.gonggam.member.domain.Member;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.property.KakaoProperties;
import company.gonggam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberSocialLoginService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoProperties kakaoProperties;

    /*
        카카오 로그인
     */
    public MemberResponseDTO.KakaoProfile kakaoLogin(String code) {

        // 토큰 발급
        String accessToken = generateAccessToken(code);

        // 사용자 정보
        MemberResponseDTO.KakaoInfoDTO profile = getKakaoProfile(accessToken);

        // 회원 확인
        Member member = memberService.findMemberByEmail(profile.kakaoAccount().email())
                .orElse(kakaoSignUp(profile));

        return new MemberResponseDTO.KakaoProfile(
                profile.properties().nickname(),
                profile.kakaoAccount().email(),
                profile.kakaoAccount().age_range(),
                profile.kakaoAccount().gender()
        );
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
            throw new ApplicationException(ErrorCode.FAILED_GET_ACCESS_TOKEN);
        }

        return response.getBody();
    }

    // TODO: AgeGroup.fromString 완성
    private Member kakaoSignUp(MemberResponseDTO.KakaoInfoDTO profile) {

        Member member = Member.builder()
                .name(profile.properties().nickname())
                .email(profile.kakaoAccount().email())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .gender(Gender.fromString(profile.kakaoAccount().gender()))
                .ageGroup(AgeGroup.fromString(profile.kakaoAccount().age_range()))
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
}
