package company.gonggam.member.controller;

import company.gonggam._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class MemberController {


    /*
        기본 회원 가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        기본 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        카카오 회원가입
     */
    @PostMapping("/kakao/signup")
    public ResponseEntity<?> kakaoSignup() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        카카오 로그인
     */
    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        네이버 회원가입
     */
    @PostMapping("/naver/signup")
    public ResponseEntity<?> naverSignup() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        네이버 로그인
     */
    @PostMapping("/naver/login")
    public ResponseEntity<?> naverLogin() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
