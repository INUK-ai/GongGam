package company.gonggam.member;

import company.gonggam._core.utils.RedisUtils;
import company.gonggam.member.domain.Member;
import company.gonggam.member.dto.MemberRequestDTO;
import company.gonggam.member.repository.MemberRepository;
import company.gonggam.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RedisUtils redisUtils;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
                12
        );

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(redisUtils.getHashValue(anyString(), anyString())).thenReturn("pass");

        // when
        memberService.signUp(requestDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void checkEmailCode() {
    }

    @Test
    void verifyEmail() {
    }

    @Test
    void login() {
    }
}