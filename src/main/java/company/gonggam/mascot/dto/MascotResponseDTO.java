package company.gonggam.mascot.dto;

import company.gonggam.mascot.domain.MascotType;

public class MascotResponseDTO {

    // 마스코트 생성
    public record initMascotDTO(
            String name,
            int level,
            MascotType mascotType
    ) {

    }

    // 메인 페이지
    public record getMascotDTO(

    ) {

    }
}
