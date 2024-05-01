package company.gonggam.mbti.repository;

import company.gonggam.mbti.domain.MBTIQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MBTIRepository extends JpaRepository<MBTIQuestion, Long>, MBTIRepositoryCustom {
}
