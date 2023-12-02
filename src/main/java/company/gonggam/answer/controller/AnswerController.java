package company.gonggam.answer.controller;

import company.gonggam._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AnswerController {

    /*
        답변을 한 질문리스트 조회
     */
    @GetMapping("/myQuestion")
    public ResponseEntity<?> getMyQuestionList() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        질문을 한 답변 확인
     */
    @GetMapping("/{answerId}")
    public ResponseEntity<?> getMyAnswerList(@PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        답변 공개 여부 수정
     */
    @PatchMapping("/{answerId}/disclosure")
    public ResponseEntity<?> changeDisclosurePolicy(@PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        답변에 대한 현재 생각 추가
     */
    @PostMapping("/{answerId}/comment")
    public ResponseEntity<?> addNewAnswer(@PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        해당 질문에 대한 답변 조회
     */
    @GetMapping("/{answerId}/otherAnswer")
    public ResponseEntity<?> getOtherAnswerList(@PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
