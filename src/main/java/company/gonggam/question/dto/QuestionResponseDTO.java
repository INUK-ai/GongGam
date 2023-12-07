package company.gonggam.question.dto;

import company.gonggam.question.domain.Question;

public class QuestionResponseDTO {

    // 일일 질문 확인
    public record getDailyQuestionDTO(
        String content
    ) {
        public getDailyQuestionDTO(Question question) {
            this(question.getContent());
        }
    }
}
