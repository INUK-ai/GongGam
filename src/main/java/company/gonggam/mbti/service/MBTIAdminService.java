package company.gonggam.mbti.service;


import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.dto.admin.MBTIAdminRequestDTO;
import company.gonggam.mbti.repository.MBTIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MBTIAdminService {

    private final MBTIRepository mbtiRepository;

    public void saveMBTIQuestion(MBTIAdminRequestDTO.saveMBTIQuestionDTO requestDTO) {

        // MBTI 질문 생성
        MBTIQuestion mbtiQuestion = MBTIQuestion.builder()
                .type(requestDTO.type())
                .question(requestDTO.question())
                .bias(requestDTO.bias())
                .build();

        // MBTI 질문 저장
        mbtiRepository.save(mbtiQuestion);
    }
}
