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
                @Min(value = 1, message = "bias는 0 이상이어야 합니다.")
                int bias
        ) {
        }
    }
}
