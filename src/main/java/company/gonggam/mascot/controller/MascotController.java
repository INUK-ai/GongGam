package company.gonggam.mascot.controller;

import company.gonggam._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MascotController {

    /*
        메인 페이지
     */
    @GetMapping("/mascot")
    public ResponseEntity<?> getMascot() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        일일 질문 확인
     */
    @GetMapping("/daily/question")
    public ResponseEntity<?> getDailyQuestion() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        일일 질문 답변
     */
    @PostMapping("/daily/question/answer")
    public ResponseEntity<?> answerDailyQuestion() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
