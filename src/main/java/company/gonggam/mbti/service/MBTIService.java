package company.gonggam.mbti.service;

import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.repository.MBTIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MBTIService {

    private final MBTIRepository mbtiRepository;

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionList(int page, int size) {

        Page<MBTIQuestion> mbtiQuestionPage = mbtiRepository.findAll(PageRequest.of(page, size));

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }
}
