package company.gonggam.mascot.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mascot.dto.MascotRequestDTO;
import company.gonggam.mascot.service.MascotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static company.gonggam._core.utils.SecurityUtils.getCurrentMemberId;
import static company.gonggam.mascot.dto.MascotResponseDTO.MainMascotListDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MascotController {

    private final MascotService mascotService;

    /*
        마스코트 생성
     */
    @PostMapping("/mascot")
    public ResponseEntity<?> initMascot(@Valid @RequestBody MascotRequestDTO.initMascotDTO requestDTO) {

        mascotService.initMascot(requestDTO, getCurrentMemberId());

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /*
        메인 페이지
     */
    @GetMapping("/mascot")
    public ResponseEntity<?> mainMascot() {

        MainMascotListDTO responseDTO = mascotService.mainMascot(getCurrentMemberId());

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
