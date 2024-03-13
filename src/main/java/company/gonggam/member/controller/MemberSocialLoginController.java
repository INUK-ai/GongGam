package company.gonggam.member.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.service.MemberSocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class MemberSocialLoginController {

    private final MemberSocialLoginService memberSocialLoginService;

    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=049ebf43b54fe431e060d0aa0a8e39af&redirect_uri=http://localhost:8080/api/auth/kakao/login
    /*
        카카오 로그인
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam(name = "code") String code) {

        MemberResponseDTO.authTokenDTO responseDTO = memberSocialLoginService.kakaoLogin(code);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        네이버 로그인
     */
    @GetMapping("/naver/login")
    public ResponseEntity<?> naverLogin(@RequestParam(name = "code") String code) {

        MemberResponseDTO.authTokenDTO responseDTO = memberSocialLoginService.naverLogin(code);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
