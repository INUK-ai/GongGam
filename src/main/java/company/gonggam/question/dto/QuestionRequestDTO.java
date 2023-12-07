package company.gonggam.question.dto;

public class QuestionRequestDTO {

    // 일일 질문 답변
    public record answerDailyQuestionDTO(
        String content
    ) {

    }
}
