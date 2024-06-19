package company.gonggam.mascot.dto;

import company.gonggam.mascot.domain.MascotType;
import company.gonggam.mascot.domain.MemberMascot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MascotResponseDTO {

    // 마스코트 생성
    public record InitMascotDTO(
            String name,
            int level,
            MascotType mascotType
    ) {

    }

    // 메인 페이지
    public record MainMascotListDTO(
            ArrayList<MemberMascotDTO> mainMascotListDTO
    ) {
        public MainMascotListDTO(List<MemberMascot> memberMascots) {
            this(memberMascots.stream()
                    .map(MemberMascotDTO::new)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        public record MemberMascotDTO(
                MascotType mascotType
        ) {
            public MemberMascotDTO(MemberMascot memberMascot) {
                this(memberMascot.getMascotType());
            }
        }
    }
}
