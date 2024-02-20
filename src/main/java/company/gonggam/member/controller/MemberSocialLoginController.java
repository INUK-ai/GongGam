package company.gonggam.member.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.service.MemberSocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class MemberSocialLoginController {

    private final MemberSocialLoginService memberSocialLoginService;

    /*
        카카오 로그인
     */
    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam(name = "code") String code) {

        MemberResponseDTO.authTokenDTO responseDTO = memberSocialLoginService.kakaoLogin(code);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        네이버 로그인
     */
    @PostMapping("/naver/login")
    public ResponseEntity<?> naverLogin(@RequestParam(name = "code") String code) {

        MemberResponseDTO.authTokenDTO responseDTO = memberSocialLoginService.naverLogin(code);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
