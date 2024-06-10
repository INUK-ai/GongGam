package company.gonggam.mascot.repository.mascot;

import company.gonggam.mascot.domain.Mascot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotRepository extends JpaRepository<Mascot, Long>, MascotRepositoryCustom {


}
