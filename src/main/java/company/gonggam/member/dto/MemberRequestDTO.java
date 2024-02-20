package company.gonggam.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberRequestDTO {

    // 기본 회원 가입
    public record signUpDTO(
            @NotBlank(message = "이름을 입력해 주세요.")
            String name,
            @Email(message = "올바른 이메일 주소를 입력해 주세요.")
            @NotBlank(message = "이메일을 입력해 주세요.")
            String email,
            @Pattern(regexp = "^(?=.*\\d).{6,}$", message = "패스워드는 최소 6자 이상이어야 하며, 숫자를 포함해야 합니다.")
            String password,
            String confirmPassword,
            String gender,
            Integer age
    ) {
    }

    // 기본 로그인
    public record loginDTO(
            @Email(message = "올바른 이메일 주소를 입력해 주세요.")
            @NotBlank(message = "이메일을 입력해 주세요.")
            String email,
            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "패스워드는 최소 8자 이상이어야 하며, 대소문자, 숫자, 특수문자를 포함해야 합니다.")
            String password
    ) {
    }
}
