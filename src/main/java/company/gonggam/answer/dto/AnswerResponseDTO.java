package company.gonggam.answer.dto;

import company.gonggam.answer.domain.Answer;
import company.gonggam.answer.domain.AnswerComment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerResponseDTO {

    // 답변을 한 질문리스트 조회
    public record getMyQuestionListDTO(
            List<MyQuestionDTO> myQuestionDTOList
    ) {
        public getMyQuestionListDTO(Page<Answer> answers) {
            this(answers.stream().map(MyQuestionDTO::new).collect(Collectors.toList()));
        }
        public record MyQuestionDTO(
                String content
        ) {
            public MyQuestionDTO(Answer answer) {
                this(answer.getQuestion().getContent());
            }
        }
    }

    // 답변 상세 조회
    public record getMyAnswerDTO(
            String questionContent,
            String answerContent,
            List<MyAnswerCommentDTO> myAnswerCommentDTOS
    ) {
        public getMyAnswerDTO(Answer answer) {
            this(
                    answer.getQuestion().getContent(),
                    answer.getContent(),
                    answer.getComments().stream()
                            .map(MyAnswerCommentDTO::new)
                            .collect(Collectors.toList())
            );
        }
        private record MyAnswerCommentDTO(
                String comment,
                LocalDateTime createdAt
        ) {
            public MyAnswerCommentDTO(AnswerComment answerComment) {
                this(answerComment.getComment(), answerComment.getCreatedAt());
            }
        }
    }

    // 해당 질문에 대한 다른 회원의 답변 조회
    public record getOtherAnswerListDTO(
            String questionContent,
            String myAnswerContent,
            List<otherAnswerDTO> otherAnswerContentList
    ) {
        public getOtherAnswerListDTO(Answer answer) {
            this(
                    answer.getQuestion().getContent(),
                    answer.getContent(),
                    answer.getQuestion().getAnswers().stream()
                            .map(otherAnswerDTO::new)
                            .collect(Collectors.toList())
            );
        }
        public record otherAnswerDTO(
                String content
        ){
            public otherAnswerDTO(Answer answer) {
                this(answer.getContent());
            }
        }
    }
}
