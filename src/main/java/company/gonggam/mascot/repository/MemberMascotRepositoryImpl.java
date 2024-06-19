package company.gonggam.mascot.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberMascotRepositoryImpl implements MemberMascotRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
