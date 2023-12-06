package company.gonggam.mascot.repository;

import company.gonggam.member.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotRepository extends CrudRepository<Member, Long>, MascotRepositoryCustom {


}
