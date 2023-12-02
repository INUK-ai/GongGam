package company.gonggam.mascot.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mascot.service.MascotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static company.gonggam.mascot.dto.MascotRequestDTO.*;
import static company.gonggam.mascot.dto.MascotResponseDTO.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MascotController {

    private final MascotService mascotService;

    /*
        메인 페이지
     */
    @GetMapping("/mascot")
    public ResponseEntity<?> getMascot() {

        getMascotDTO responseDTO = mascotService.getMascot();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        일일 질문 확인
     */
    @GetMapping("/daily/question")
    public ResponseEntity<?> getDailyQuestion() {

        getDailyQuestionDTO responseDTO = mascotService.getDailyQuestion();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        일일 질문 답변
     */
    @PostMapping("/daily/question/answer")
    public ResponseEntity<?> answerDailyQuestion(answerDailyQuestionDTO requestDTO) {

        mascotService.answerDailyQuestion(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
