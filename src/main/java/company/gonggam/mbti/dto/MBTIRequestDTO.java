package company.gonggam.mbti.dto;

import jakarta.validation.constraints.NotBlank;

public class MBTIRequestDTO {

    public record MBTIInterimResultDTO(
            @NotBlank(message = "이메일을 입력해 주세요.")
            String type,
            @NotBlank(message = "이메일을 입력해 주세요.")
            int ratio
    ) {
    }
}
