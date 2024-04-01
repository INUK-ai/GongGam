package company.gonggam.mascot.service;

import company.gonggam.mascot.repository.MascotRepository;
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

    private final MascotRepository mascotRepository;

    /*
        마스코트 생성
     */


    /*
        메인 페이지
        - mascot 반환
     */
    public getMascotDTO getMascot() {
        
        // 회원 확인
        
        // 해당 회원의 Mascot 가져오기

        return new getMascotDTO();
    }
}
