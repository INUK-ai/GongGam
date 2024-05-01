package company.gonggam.mbti.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MBTIRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;
}
