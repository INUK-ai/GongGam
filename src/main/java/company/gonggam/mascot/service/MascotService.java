package company.gonggam.mascot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static company.gonggam.mascot.dto.MascotResponseDTO.getMascotDTO;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MascotService {

    /*
        메인 페이지
        - mascot 반환
     */
    public getMascotDTO getMascot() {

        return new getMascotDTO();
    }
}
