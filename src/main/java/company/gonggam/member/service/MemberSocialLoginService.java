package company.gonggam.member.service;

import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberSocialLoginService {

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;
    @Value("${kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    /*
        카카오 로그인
     */
    public MemberResponseDTO.authTokenDTO kakaoLogin(String code) {

        // 회원 확인

        // 토큰 발급

        //return new MemberResponseDTO.authTokenDTO();
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
