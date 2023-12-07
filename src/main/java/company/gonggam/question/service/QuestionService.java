package company.gonggam.question.service;

import company.gonggam.question.domain.Question;
import company.gonggam.question.dto.QuestionRequestDTO;
import company.gonggam.question.dto.QuestionResponseDTO;
import company.gonggam.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /*
        일일 질문 확인
        - 해당 Mascot 의 MascotQuestion 에서 질문 하나 반환
     */
    public QuestionResponseDTO.getDailyQuestionDTO getDailyQuestion() {

        // 회원 확인

        // 마스코트 확인

        // MascotQuestion 확인

        // MascotQuestionList 에서 하나 가져와서 반환
        Question question = null;

        return new QuestionResponseDTO.getDailyQuestionDTO(question);
    }

    /*
        일일 질문 답변
     */
    public void answerDailyQuestion(QuestionRequestDTO.answerDailyQuestionDTO requestDTO) {

        // 회원 확인

        // 마스코트 확인

        // 답변 저장

        // MascotQuestion 에서 해당 Question 삭제
    }
}
