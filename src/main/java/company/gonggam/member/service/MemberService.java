package company.gonggam.member.service;

import company.gonggam.member.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    /*
        기본 회원 가입
     */
    public void signUp(MemberRequestDTO.signUpDTO requestDTO) {

    }

    public void login(MemberRequestDTO.loginDTO requestDTO) {

    }

    public void kakaoSignUp(MemberRequestDTO.kakaoSignUpDTO requestDTO) {

    }

    public void kakaoLogin(MemberRequestDTO.kakaoLoginDTO requestDTO) {

    }

    public void naverSignUp(MemberRequestDTO.naverSignUpDTO requestDTO) {

    }

    public void naverLogin(MemberRequestDTO.naverLoginDTO requestDTO) {

    }
}
