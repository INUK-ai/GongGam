package company.gonggam.member.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static company.gonggam.member.dto.MemberRequestDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    /*
        기본 회원 가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody signUpDTO requestDTO) {

        memberService.signUp(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        이메일 인증번호 발송
     */
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmail(@Valid @RequestParam("email") String email) {

        memberService.checkEmailCode(email);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        기본 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody loginDTO requestDTO) {

        MemberResponseDTO.authTokenDTO responseDTO = memberService.login(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        RefreshToken 발급
     */
}
