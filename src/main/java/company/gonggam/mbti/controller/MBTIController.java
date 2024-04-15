package company.gonggam.mbti.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.service.MBTIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mbti")
public class MBTIController {

    private final MBTIService mbtiService;

    /*
        MBTI 질문 가져오기
     */
    @GetMapping("/question")
    public ResponseEntity<?> getMBTIQuestionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        MBTIResponseDTO.MBTIQuestionListDTO responseDTO = mbtiService.getMBTIQuestionList(page, size);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
