package company.gonggam.question.service;

import company.gonggam.question.dto.QuestionRequestDTO;
import company.gonggam.question.dto.QuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class QuestionService {

    /*
        일일 질문 확인
        - 해당 Mascot의 MascotQuestion에서 질문 하나 반환
     */
    public QuestionResponseDTO.getDailyQuestionDTO getDailyQuestion() {

        return new QuestionResponseDTO.getDailyQuestionDTO();
    }

    /*
        일일 질문 답변
     */
    public void answerDailyQuestion(QuestionRequestDTO.answerDailyQuestionDTO requestDTO) {

    }
}
