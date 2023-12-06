package company.gonggam.mascot.controller;

import company.gonggam._core.utils.ApiUtils;
import company.gonggam.mascot.service.MascotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static company.gonggam.mascot.dto.MascotResponseDTO.getMascotDTO;

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
}
