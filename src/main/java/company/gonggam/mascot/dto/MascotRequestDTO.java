package company.gonggam.mascot.dto;

import jakarta.validation.constraints.NotBlank;

public class MascotRequestDTO {
    public record initMascotDTO(
            @NotBlank
            String mbtiType,
            @NotBlank
            String mascotName
    ) {
    }
}
