package company.gonggam.member.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static company.gonggam.member.dto.MemberRequestDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    /*
        기본 회원 가입
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(signUpDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        기본 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(loginDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        카카오 회원가입
     */
    @PostMapping("/kakao/signUp")
    public ResponseEntity<?> kakaoSignUp(kakaoSignUpDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        카카오 로그인
     */
    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(kakaoLoginDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        네이버 회원가입
     */
    @PostMapping("/naver/signUp")
    public ResponseEntity<?> naverSignUp(naverSignUpDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        네이버 로그인
     */
    @PostMapping("/naver/login")
    public ResponseEntity<?> naverLogin(naverLoginDTO requestDTO) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
