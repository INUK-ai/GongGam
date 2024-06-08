package company.gonggam.mbti.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.dto.MBTIRequestDTO;
import company.gonggam.mbti.dto.MBTIResponseDTO;
import company.gonggam.mbti.repository.MBTIRepository;
import company.gonggam.redis.domain.MBTIResult;
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

    private static final String[] MBTIType = {"IE", "NS", "FT", "PJ"};

    public MBTIResponseDTO.MBTIQuestionListDTO getFirstMBTIQuestionList(int page, int size) {

        // 리스트 가져오기
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        // 중간결과를 저장할 객체 생성 및 저장
        MBTIResult.builder().build();

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionListAndSaveInterimResult(MBTIRequestDTO.@Valid MBTIMemberAnswerListDTO requestDTO, int page, int size, Long currentMemberId) {

        // 중간 결과 불러오기
        MBTIResult interimResult = mbtiInterimResultRedisRepository.findbyInterimResultId(currentMemberId)
                .orElseGet(() -> {
                    Map<String, Integer> initialScores = new HashMap<>();
                    initialScores.put("IE", 0);
                    initialScores.put("NS", 0);
                    initialScores.put("FT", 0);
                    initialScores.put("PJ", 0);

                    MBTIResult newInterimResult = MBTIResult.builder()
                            .id(currentMemberId)
                            .scores(initialScores)
                            .build();
                    mbtiInterimResultRedisRepository.save(newInterimResult);

                    return newInterimResult;
                });

        // 중간 결과 연산
        calculateMBTIScores(requestDTO, interimResult);

        // 페이징
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    @Async
    protected void calculateMBTIScores(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, MBTIResult mbtiResult) {

        // 추가 항목 합산
        requestDTO.mbtiMemberAnswerDTOList().forEach(mbtiMemberAnswerDTO -> {
            String type = mbtiMemberAnswerDTO.type();
            int bias = mbtiMemberAnswerDTO.bias();
            int ratio = mbtiMemberAnswerDTO.ratio();

            mbtiResult.getScores().put(type, mbtiResult.getScores().getOrDefault(type, 0) + bias * ratio);
            mbtiResult.getTotal_bias().put(type, mbtiResult.getTotal_bias().getOrDefault(type, 0) + bias);
        });
    }

    protected Page<MBTIQuestion> getMbtiQuestionPage(int page, int size) {
        return mbtiRepository.findAll(PageRequest.of(page, size));
    }

    public MBTIResponseDTO.MBTIResultDTO getMBTIResult(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, Long currentMemberId) {

        MBTIResult mbtiResult = mbtiInterimResultRedisRepository.findbyInterimResultId(currentMemberId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.CANT_LOAD_MBTI_INTERIM_RESULT));

        calculateMBTIScores(requestDTO, mbtiResult);

        return getMBTIType(mbtiResult);
    }

    private MBTIResponseDTO.MBTIResultDTO getMBTIType(MBTIResult mbtiResult) {

        Map<String, Integer> scores = mbtiResult.getScores();
        Map<String, Integer> total_bias = mbtiResult.getTotal_bias();

        StringBuilder mbtiType = new StringBuilder();
        Map<Character, Integer> result = new HashMap<>();

        for(String type : MBTIType) {
            int score = scores.get(type);
            int bias = total_bias.get(type);

            int type_index = score > 0 ? 1 : 0;
            char type_result = type.charAt(type_index);
            int type_ratio = (Math.abs(score) / bias) * 100;

            mbtiType.append(type_result);
            result.put(type_result, type_ratio);
        }

        return new MBTIResponseDTO.MBTIResultDTO(mbtiType.toString(), result);
    }
}
