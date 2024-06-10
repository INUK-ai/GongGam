package company.gonggam.mascot.repository.memberMascot;

import company.gonggam.mascot.domain.MemberMascot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMascotRepository extends JpaRepository<MemberMascot, Long> {
}
