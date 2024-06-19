package company.gonggam.redis.repository;

import company.gonggam.redis.domain.MBTIResult;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MBTIInterimResultRedisRepository extends CrudRepository<MBTIResult, Long> {

    Optional<MBTIResult> findById(Long id);
}
