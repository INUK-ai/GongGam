package company.gonggam.question.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.question.dto.QuestionRequestDTO;
import company.gonggam.question.dto.QuestionResponseDTO;
import company.gonggam.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    /*
        일일 질문 확인
     */
    @GetMapping("/daily/question")
    public ResponseEntity<?> getDailyQuestion() {

        QuestionResponseDTO.getDailyQuestionDTO responseDTO = questionService.getDailyQuestion();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        일일 질문 답변
     */
    @PostMapping("/daily/question/answer")
    public ResponseEntity<?> answerDailyQuestion(QuestionRequestDTO.answerDailyQuestionDTO requestDTO) {

        questionService.answerDailyQuestion(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
