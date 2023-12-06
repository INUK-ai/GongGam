package company.gonggam.question.repository;

import company.gonggam.question.domain.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>, QuestionRepositoryCustom {
}
