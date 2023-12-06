package company.gonggam.mascot.repository;

import company.gonggam.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotRepository extends JpaRepository<Member, Long>, MascotRepositoryCustom {


}
