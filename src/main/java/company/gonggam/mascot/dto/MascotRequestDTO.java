package company.gonggam.mascot.dto;

import jakarta.validation.constraints.NotBlank;

public class MascotRequestDTO {
    public record initMascotDTO(
            @NotBlank
            String first_type,
            @NotBlank
            String second_type,
            @NotBlank
            String third_type,
            @NotBlank
            String fourth_type
    ) {
    }
}
