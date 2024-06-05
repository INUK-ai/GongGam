package company.gonggam.redis.repository;

import company.gonggam.redis.domain.MBTIInterimResult;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MBTIInterimResultRedisRepository extends CrudRepository<MBTIInterimResult, Long> {

    Optional<MBTIInterimResult> findbyInterimResultId(Long interimResultId);
}
