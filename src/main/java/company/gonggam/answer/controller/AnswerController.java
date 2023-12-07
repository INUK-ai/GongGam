package company.gonggam.answer.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static company.gonggam.answer.dto.AnswerRequestDTO.*;
import static company.gonggam.answer.dto.AnswerResponseDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;

    /*
        답변을 한 질문리스트 조회
     */
    @GetMapping("/myQuestion")
    public ResponseEntity<?> getMyQuestionList() {

        getMyQuestionListDTO responseDTO = answerService.getMyQuestionList();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        답변 상세 조회
     */
    @GetMapping("/{answerId}")
    public ResponseEntity<?> getMyAnswer(@PathVariable("answerId") Long answerId) {

        getMyAnswerDTO responseDTO = answerService.getMyAnswer(answerId);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        답변 공개 여부 수정
     */
    @PatchMapping("/{answerId}/disclosure")
    public ResponseEntity<?> changeDisclosurePolicy(
            @PathVariable("answerId") Long answerId,
            changeDisclosurePolicyDTO requestDTO
            ) {

        answerService.changeDisclosurePolicy(answerId, requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        답변에 대한 현재 생각 추가
     */
    @PostMapping("/{answerId}/comment")
    public ResponseEntity<?> addComment(
            @PathVariable("answerId") Long answerId,
            addCommentDTO requestDTO
            ) {

        answerService.addComment(answerId, requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        해당 질문에 대한 다른 회원의 답변 조회
     */
    @GetMapping("/{answerId}/otherAnswer")
    public ResponseEntity<?> getOtherAnswerList(@PathVariable("answerId") Long answerId) {

        getOtherAnswerListDTO responseDTO = answerService.getOtherAnswerList(answerId);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
