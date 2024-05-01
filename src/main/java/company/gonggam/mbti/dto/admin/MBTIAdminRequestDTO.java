package company.gonggam.mbti.dto.admin;

import jakarta.validation.constraints.NotBlank;

public class MBTIAdminRequestDTO {

    public record saveMBTIQuestionDTO (
            @NotBlank(message = "유형을 선택해 주세요.")
            String type,
            @NotBlank(message = "질문을 입력해 주세요.")
            String question,
            int bias
    ) {
    }
}
