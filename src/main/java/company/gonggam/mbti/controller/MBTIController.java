package company.gonggam.mbti.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mbti.dto.MBTIRequestDTO;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.service.MBTIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static company.gonggam._core.utils.SecurityUtils.getCurrentMemberId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mbti")
public class MBTIController {

    private final MBTIService mbtiService;

    /*
        MBTI 질문 첫 페이지 가져오기
     */
    @GetMapping("/question")
    public ResponseEntity<?> getFirstMBTIQuestionList(
            @RequestParam(defaultValue = "10") int size
    ) {

        int page = 0;

        MBTIResponseDTO.MBTIQuestionListDTO responseDTO = mbtiService.getFirstMBTIQuestionList(page, size);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        MBTI 질문 가져오기
        중간 결과도 저장
     */
    @PostMapping("/question")
    public ResponseEntity<?> getMBTIQuestionList(
            @Valid @RequestBody MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        MBTIResponseDTO.MBTIQuestionListDTO responseDTO = mbtiService.getMBTIQuestionListAndSaveInterimResult(requestDTO, page, size, getCurrentMemberId());

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        결과 확인
     */
    @PostMapping("/result")
    public ResponseEntity<?> getMBTIResult(@Valid @RequestBody MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO) {

        MBTIResponseDTO.MBTIResultDTO responseDTO = mbtiService.getMBTIResult(requestDTO, getCurrentMemberId());

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
