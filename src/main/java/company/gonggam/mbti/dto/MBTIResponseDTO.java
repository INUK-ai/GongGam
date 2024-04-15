package company.gonggam.mbti.dto;

import company.gonggam.mbti.domain.MBTIQuestion;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MBTIResponseDTO {

    public record MBTIQuestionListDTO(
            List<MBTIQuestionDTO> mbtiQuestionList
    ) {
        public MBTIQuestionListDTO(Page<MBTIQuestion> mbtiQuestions) {
            this(mbtiQuestions.stream().map(MBTIQuestionDTO::new).collect(Collectors.toList()));
        }

        public record MBTIQuestionDTO(
            String question
        ) {
            public MBTIQuestionDTO(MBTIQuestion mbtiQuestion) {
                this(mbtiQuestion.getQuestion());
            }
        }
    }
}
