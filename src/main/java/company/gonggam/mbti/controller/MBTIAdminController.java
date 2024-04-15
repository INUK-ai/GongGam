package company.gonggam.mbti.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mbti.dto.MBTIRequestDTO;
import company.gonggam.mbti.service.MBTIAdminService;
import company.gonggam.member.dto.MemberRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mbti/admin")
public class MBTIAdminController {

    private final MBTIAdminService mbtiAdminService;

    /*
        MBTI 질문 개별 추가
     */
    @PostMapping("/question")
    public ResponseEntity<?> signUp(@Valid @RequestBody MBTIRequestDTO.saveMBTIQuestionDTO requestDTO) {

        mbtiAdminService.saveMBTIQuestion(requestDTO);
        
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        MBTI 질문 리스트 수정
     */
}
