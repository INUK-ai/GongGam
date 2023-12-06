package company.gonggam.mascot.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MascotRepositoryImpl implements MascotRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
