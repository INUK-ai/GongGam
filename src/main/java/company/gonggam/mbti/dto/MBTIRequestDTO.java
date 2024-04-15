package company.gonggam.mbti.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class MBTIRequestDTO {

    public record MBTIInterimResultListDTO(
            List<MBTIInterimResultDTO> mbtiInterimResultDTOList
    ) {
        public record MBTIInterimResultDTO(
                @NotBlank(message = "유형을 입력해 주세요.")
                String type,
                @Min(value = 0, message = "bias는 0 이상이어야 합니다.")
                @Max(value = 10, message = "bias는 10 이하여야 합니다.")
                int bias,
                @Positive(message = "비율은 양수여야 합니다.")
                int ratio
        ) {
        }
    }
}
