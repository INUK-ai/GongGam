package company.gonggam.mbti.dto;

import company.gonggam.mbti.domain.MBTIQuestion;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public class MBTIResponseDTO {

    public record MBTIQuestionListDTO(
            List<MBTIQuestionDTO> mbtiQuestionList
    ) {
        public MBTIQuestionListDTO(Page<MBTIQuestion> mbtiQuestions) {
            this(mbtiQuestions.stream()
                    .map(MBTIQuestionDTO::new)
                    .toList());
        }

        public record MBTIQuestionDTO(
            String type,
            String question,
            int bias
        ) {
            public MBTIQuestionDTO(MBTIQuestion mbtiQuestion) {
                this(mbtiQuestion.getType(), mbtiQuestion.getQuestion(), mbtiQuestion.getBias());
            }
        }
    }

    public record MBTIResultDTO(
            String mbtiType,
            Map<Character, Integer> mbtiResult
    ) {
    }
}