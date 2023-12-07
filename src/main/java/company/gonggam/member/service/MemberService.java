package company.gonggam.member.service;

import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    /*
        기본 회원 가입
     */
    public void signUp(MemberRequestDTO.signUpDTO requestDTO) {

        // 회원 생성

        // 회원 저장
    }

    /*
        기본 로그인
     */
    public MemberResponseDTO.authTokenDTO login(MemberRequestDTO.loginDTO requestDTO) {

        // 회원 확인

        // 토큰 발급

        return new MemberResponseDTO.authTokenDTO();
    }

    /*
        카카오 회원 가입
     */
    public void kakaoSignUp(MemberRequestDTO.kakaoSignUpDTO requestDTO) {

    }

    /*
        카카오 로그인
     */
    public MemberResponseDTO.authTokenDTO kakaoLogin(MemberRequestDTO.kakaoLoginDTO requestDTO) {

        // 회원 확인

        // 토큰 발급

        return new MemberResponseDTO.authTokenDTO();
    }

    /*
        네이버 회원 가입
     */
    public void naverSignUp(MemberRequestDTO.naverSignUpDTO requestDTO) {

    }

    /*
        네이버 로그인
     */
    public MemberResponseDTO.authTokenDTO naverLogin(MemberRequestDTO.naverLoginDTO requestDTO) {

        // 회원 확인

        // 토큰 발급

        return new MemberResponseDTO.authTokenDTO();
    }
}
