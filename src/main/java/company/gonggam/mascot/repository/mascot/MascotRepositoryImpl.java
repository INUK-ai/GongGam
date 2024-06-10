package company.gonggam.mascot.repository.mascot;

import com.querydsl.jpa.impl.JPAQueryFactory;
import company.gonggam.mascot.domain.Mascot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MascotRepositoryImpl implements MascotRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Mascot> findByMascotType(String mbtiType) {
        return Optional.empty();
    }
}
