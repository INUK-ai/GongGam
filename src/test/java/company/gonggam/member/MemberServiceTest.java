package company.gonggam.member;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam._core.utils.RedisUtils;
import company.gonggam.member.domain.Member;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.repository.MemberRepository;
import company.gonggam.member.service.EmailService;
import company.gonggam.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RedisUtils redisUtils;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("기본 회원 가입")
    void signUp() {

        // given
        MemberRequestDTO.signUpDTO requestDTO = new MemberRequestDTO.signUpDTO(
                "test",
                "test@test.com",
                "test1234",
                "test1234",
                "male",
                "10~19"
        );

        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        String encodedPassword = passwordEncoder.encode("test1234");
        when(passwordEncoder.encode("test1234")).thenReturn(encodedPassword);
        when(passwordEncoder.matches("test1234", encodedPassword)).thenReturn(true);

        when(redisUtils.getHashValue("email:test@test.com", "verify")).thenReturn("true");

        // when
        memberService.signUp(requestDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("기본 회원 가입 실패 - 이메일 중복")
    void signUp_duplicatedEmail() {

        // given
        MemberRequestDTO.signUpDTO requestDTO = new MemberRequestDTO.signUpDTO(
                "test",
                "test@test.com",
                "test1234",
                "test1234",
                "male",
                "10~19"
        );

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(Member.builder().build()));

        // when
        ApplicationException exception = assertThrows(
                ApplicationException.class, () -> memberService.signUp(requestDTO)
        );

        // then
        assertEquals(ErrorCode.SAME_EMAIL, exception.getErrorCode());
    }

    @Test
    @DisplayName("기본 회원 가입 실패 - 유효하지 않은 비밀번호")
    void signUp_invalidPassword() {

        // 입력된 비밀번호와 확인 비밀번호가 일치하는지 확인

        // given
        MemberRequestDTO.signUpDTO requestDTO = new MemberRequestDTO.signUpDTO(
                "test",
                "test@test.com",
                "test1234",
                "test12341",
                "male",
                "10~19"
        );

        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        // when
        ApplicationException exception = assertThrows(
                ApplicationException.class, () -> memberService.signUp(requestDTO)
        );

        // then
        assertEquals(ErrorCode.INVALID_PASSWORD, exception.getErrorCode());
    }

    @Test
    @DisplayName("기본 회원 가입 실패 - 인증 되지 않은 이메일")
    void signUp_notVerifiedEmail() {

        // given
        MemberRequestDTO.signUpDTO requestDTO = new MemberRequestDTO.signUpDTO(
                "test",
                "test@test.com",
                "test1234",
                "test1234",
                "male",
                "10~19"
        );

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        String encodedPassword = passwordEncoder.encode("test1234");
        when(passwordEncoder.encode("test1234")).thenReturn(encodedPassword);
        when(passwordEncoder.matches("test1234", encodedPassword)).thenReturn(true);

        when(redisUtils.getHashValue("email:test@test.com", "verify")).thenReturn(null);

        // when
        ApplicationException exception = assertThrows(
                ApplicationException.class, () -> memberService.signUp(requestDTO)
        );

        // then
        assertEquals(ErrorCode.INVALID_EMAIL, exception.getErrorCode());
    }

    @Test
    @DisplayName("이메일 인증번호 발송")
    void checkEmail() {

        // given
        String email = "test@test.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        memberService.checkEmailCode(email);

        // then
        verify(memberRepository, times(1)).findByEmail(email);
        verify(emailService, times(1)).sendEmailCode(email);
    }

    @Test
    @DisplayName("이메일 인증번호 전송 실패 - 중복된 이메일")
    void checkEmailCode_duplicatedEmail() {
        // given
        String email = "test@test.com";

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(Member.builder().build()));

        // when & then
        ApplicationException exception = assertThrows(ApplicationException.class, () -> memberService.checkEmailCode(email));
        assertEquals(ErrorCode.SAME_EMAIL, exception.getErrorCode());

        verify(memberRepository, times(1)).findByEmail(email);
        verify(emailService, times(0)).sendEmailCode(email);  // 중복된 이메일이면 이메일 전송이 호출되지 않음
    }

    @Test
    @DisplayName("이메일 인증번호 확인")
    void certifyEmail() {
        // given
        String email = "test@test.com";
        String userCode = "123456";
        String redisCode = "123456";
        MemberRequestDTO.certifyEmailDTO requestDTO = new MemberRequestDTO.certifyEmailDTO(email, userCode);

        when(redisUtils.getHashValue("email:" + email, "code")).thenReturn(redisCode);

        // when
        memberService.certifyEmail(requestDTO);

        // then
        verify(redisUtils, times(1)).getHashValue("email:" + email, "code");
        verify(redisUtils, times(1)).setEmailKey("email:" + email, "verify", "true", 7L, TimeUnit.DAYS);
    }

    @Test
    @DisplayName("이메일 인증번호 확인 실패 - 유효하지 않은 인증코드")
    void certifyEmail_success() {
        // given
        String email = "test@test.com";
        String userCode = "123456";
        String redisCode = "123457";
        MemberRequestDTO.certifyEmailDTO requestDTO = new MemberRequestDTO.certifyEmailDTO(email, userCode);

        when(redisUtils.getHashValue("email:" + email, "code")).thenReturn(redisCode);


        // when
        ApplicationException exception = assertThrows(
                ApplicationException.class, () -> memberService.certifyEmail(requestDTO)
        );

        // then
        assertEquals(ErrorCode.INVALID_EMAIL_CODE, exception.getErrorCode());
    }
}