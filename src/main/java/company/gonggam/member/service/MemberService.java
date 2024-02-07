package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.member.domain.AgeGroup;
import company.gonggam.member.domain.Gender;
import company.gonggam.member.domain.Member;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.dto.MemberResponseDTO;
import company.gonggam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /*
        기본 회원 가입
     */
    @Transactional
    public void signUp(MemberRequestDTO.signUpDTO requestDTO) {

        // TODO: 예외 처리
        // 이메일 중복
        checkDuplicatedEmail(requestDTO.email());

        // 비밀번호 확인
        // 이메일 확인은 따로 API를 만들 것
        // 이메일 확인은 이후 verified 여부를 redis에서 가져와 확인

        // 회원 생성
        Member member = newMember(requestDTO);

        // 회원 저장
        memberRepository.save(member);
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            throw new ApplicationException(ErrorCode.SAME_EMAIL);
        }
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

    // 회원 생성
    private Member newMember(MemberRequestDTO.signUpDTO requestDTO) {
        return Member.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .gender(Gender.fromString(requestDTO.gender()))
                .ageGroup(AgeGroup.fromInt(requestDTO.age()))
                .build();
    }
}
