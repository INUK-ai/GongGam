package company.gonggam.question.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import company.gonggam.mascot.domain.MascotType;
import company.gonggam.question.domain.QQuestion;
import company.gonggam.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Question> findRandomQuestionsByTypeAndLevel(MascotType mascotType, int level, int size) {

        QQuestion question = QQuestion.question;

        return jpaQueryFactory.selectFrom(question)
                .where(question.mascotType.eq(mascotType)
                        .and(question.level.eq(level)))
                .orderBy(Expressions.numberTemplate(Double.class, "function('RAND')").asc())
                .limit(size)
                .fetch();
    }
}
