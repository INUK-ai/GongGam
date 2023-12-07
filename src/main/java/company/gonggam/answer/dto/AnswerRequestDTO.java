package company.gonggam.answer.dto;

public class AnswerRequestDTO {

    // 답변 공개 여부 수정
    public record changeDisclosurePolicyDTO(
            boolean disclosure
    ) {
    }

    // 답변에 대한 현재 생각 추가
    public record addCommentDTO(
            String comment
    ) {
    }
}
