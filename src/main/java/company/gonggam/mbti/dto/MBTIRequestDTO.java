package company.gonggam.mbti.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MBTIRequestDTO {

    public record saveMBTIQuestionDTO (
            @NotBlank(message = "유형을 선택해 주세요.")
            String type,
            @NotBlank(message = "질문을 입력해 주세요.")
            String question,
            int bias
    ) {
    }
}
