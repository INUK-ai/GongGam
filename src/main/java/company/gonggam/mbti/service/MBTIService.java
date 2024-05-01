package company.gonggam.mbti.service;

import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.dto.MBTIRequestDTO;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.repository.MBTIRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MBTIService {

    private final MBTIRepository mbtiRepository;

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionList(int page, int size) {

        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionListAndSaveInterimResult(MBTIRequestDTO.@Valid MBTIInterimResultListDTO requestDTO, int page, int size) {

        // 중간 결과 임시 저장
        saveInterimResult(requestDTO);

        // 페이징
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    @Async
    protected void saveInterimResult(MBTIRequestDTO.MBTIInterimResultListDTO requestDTO) {

    }

    protected Page<MBTIQuestion> getMbtiQuestionPage(int page, int size) {
        return mbtiRepository.findAll(PageRequest.of(page, size));
    }
}
