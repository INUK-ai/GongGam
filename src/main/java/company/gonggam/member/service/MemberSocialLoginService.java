package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.property.KakaoProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberSocialLoginService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoProperties kakaoProperties;

    /*
        카카오 로그인
     */
    public MemberResponseDTO.authTokenDTO kakaoLogin(String code) {

        // 토큰 발급
        String accessToken;

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

        accessToken = response.getBody().accessToken();

        return null;
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
