package company.gonggam.mbti.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mbti.dto.MBTIAdminRequestDTO;
import company.gonggam.mbti.service.MBTIService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> getMBTIQuestionList() {



        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
