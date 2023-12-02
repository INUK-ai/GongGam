package company.gonggam.question.service;

import company.gonggam.question.dto.QuestionRequestDTO;
import company.gonggam.question.dto.QuestionResponseDTO;

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
