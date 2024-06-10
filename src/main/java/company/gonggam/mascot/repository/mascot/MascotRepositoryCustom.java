package company.gonggam.mascot.repository.mascot;

import company.gonggam.mascot.domain.Mascot;

import java.util.Optional;

public interface MascotRepositoryCustom {
    Optional<Mascot> findByMascotType(String mbtiType);
}
