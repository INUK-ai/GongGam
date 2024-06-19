package company.gonggam.question.repository;

import company.gonggam.mascot.domain.MascotType;
import company.gonggam.question.domain.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findRandomQuestionsByTypeAndLevel(MascotType mascotType, int level, int size);
}
