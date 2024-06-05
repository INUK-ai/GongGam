package company.gonggam.mbti.service;

import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.dto.MBTIRequestDTO;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.repository.MBTIRepository;
import company.gonggam.redis.domain.MBTIInterimResult;
import company.gonggam.redis.repository.MBTIInterimResultRedisRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MBTIService {

    private final MBTIRepository mbtiRepository;
    private final MBTIInterimResultRedisRepository mbtiInterimResultRedisRepository;

    public MBTIResponseDTO.MBTIQuestionListDTO getFirstMBTIQuestionList(int page, int size) {

        // 리스트 가져오기
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        // 중간결과를 저장할 객체 생성 및 저장
        MBTIInterimResult.builder().build();

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionListAndSaveInterimResult(MBTIRequestDTO.@Valid MBTIMemberAnswerListDTO requestDTO, int page, int size, Long currentMemberId) {

        // 중간 결과 불러오기
        MBTIInterimResult interimResult = mbtiInterimResultRedisRepository.findbyInterimResultId(currentMemberId)
                .orElseGet(() -> {
                    Map<String, Integer> initialScores = new HashMap<>();
                    initialScores.put("IE", 0);
                    initialScores.put("NS", 0);
                    initialScores.put("FT", 0);
                    initialScores.put("PJ", 0);

                    MBTIInterimResult newInterimResult = MBTIInterimResult.builder()
                            .id(currentMemberId)
                            .scores(initialScores)
                            .build();
                    mbtiInterimResultRedisRepository.save(newInterimResult);

                    return newInterimResult;
                });

        // 중간 결과 연산
        saveInterimResult(requestDTO, interimResult);

        // 페이징
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    @Async
    protected void saveInterimResult(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, MBTIInterimResult interimResult) {

        // 추가 항목 합산
        requestDTO.mbtiMemberAnswerDTOList().forEach(mbtiMemberAnswerDTO -> {
            String type = mbtiMemberAnswerDTO.type();
            int bias = mbtiMemberAnswerDTO.bias();
            int ratio = mbtiMemberAnswerDTO.ratio();

            interimResult.getScores().put(type, interimResult.getScores().getOrDefault(type, 0) + bias * ratio);
            interimResult.getTotal_bias().put(type, interimResult.getTotal_bias().getOrDefault(type, 0) + bias);
        });
    }

    protected Page<MBTIQuestion> getMbtiQuestionPage(int page, int size) {
        return mbtiRepository.findAll(PageRequest.of(page, size));
    }
}
