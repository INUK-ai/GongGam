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
        MBTIResult mbtiResult = MBTIResult.builder()
                .scores(new HashMap<>())
                .total_bias(new HashMap<>())
                .build();

        mbtiInterimResultRedisRepository.save(mbtiResult);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    public MBTIResponseDTO.MBTIQuestionListDTO getMBTIQuestionListAndSaveInterimResult(MBTIRequestDTO.@Valid MBTIMemberAnswerListDTO requestDTO, int page, int size, Long currentMemberId) {

        // 중간 결과 불러오기
        MBTIResult interimResult = mbtiInterimResultRedisRepository.findById(currentMemberId)
                .orElseGet(() -> {
                    MBTIResult newInterimResult = MBTIResult.builder()
                            .id(currentMemberId)
                            .scores(new HashMap<>())
                            .total_bias(new HashMap<>())
                            .build();

                    mbtiInterimResultRedisRepository.save(newInterimResult);

                    return newInterimResult;
                });

        // 모든 질문에 답변했는지 확인
//        if(!checkAnsweredAllQuestions(requestDTO, size)) {
//            throw new ApplicationException(ErrorCode.NOT_ALL_QUESTIONS_ANSWERED);
//        }

        // 중간 결과 연산
        calculateMBTIScores(requestDTO, interimResult);

        // 페이징
        Page<MBTIQuestion> mbtiQuestionPage = getMbtiQuestionPage(page, size);

        return new MBTIResponseDTO.MBTIQuestionListDTO(mbtiQuestionPage);
    }

    private boolean checkAnsweredAllQuestions(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, int size) {
        return requestDTO.mbtiMemberAnswerDTOList().size() == size;
    }

    @Async
    protected void calculateMBTIScores(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, MBTIResult mbtiResult) {

        Map<String, Integer> updateScores = new HashMap<>(mbtiResult.getScores());
        Map<String, Integer> updateTotalBias = new HashMap<>(mbtiResult.getTotal_bias());

        // 추가 항목 합산
        requestDTO.mbtiMemberAnswerDTOList().forEach(mbtiMemberAnswerDTO -> {
            String type = mbtiMemberAnswerDTO.type();
            int bias = mbtiMemberAnswerDTO.bias();
            int ratio = mbtiMemberAnswerDTO.ratio();

            updateScores.put(type, updateScores.getOrDefault(type, 0) + bias * ratio);
            updateTotalBias.put(type, updateTotalBias.getOrDefault(type, 0) + bias);
        });

        MBTIResult updatedResult = MBTIResult.builder()
                .id(mbtiResult.getId())
                .scores(updateScores)
                .total_bias(updateTotalBias)
                .build();

        mbtiInterimResultRedisRepository.save(updatedResult);
    }

    protected Page<MBTIQuestion> getMbtiQuestionPage(int page, int size) {
        return mbtiRepository.findAll(PageRequest.of(page, size));
    }

    public MBTIResponseDTO.MBTIResultDTO getMBTIResult(MBTIRequestDTO.MBTIMemberAnswerListDTO requestDTO, Long currentMemberId) {

        MBTIResult mbtiResult = mbtiInterimResultRedisRepository.findById(currentMemberId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.CANT_LOAD_MBTI_INTERIM_RESULT));

        calculateMBTIScores(requestDTO, mbtiResult);

        return getMBTIType(mbtiResult);
    }

    // TODO: 계산 로직 손 볼 것
    private MBTIResponseDTO.MBTIResultDTO getMBTIType(MBTIResult mbtiResult) {

        Map<String, Integer> scores = mbtiResult.getScores();
        Map<String, Integer> total_bias = mbtiResult.getTotal_bias();

        StringBuilder mbtiType = new StringBuilder();
        Map<Character, Integer> result = new HashMap<>();

        for(String type : MBTIType) {
            int score = scores.getOrDefault(type, 0);
            int bias = total_bias.getOrDefault(type, 1);

            int type_index = score > 0 ? 1 : 0;
            char type_result = type.charAt(type_index);
            int type_ratio = (Math.abs(score) * 100) / Math.max(bias, 1);

            mbtiType.append(type_result);
            result.put(type_result, type_ratio);
        }

        mbtiInterimResultRedisRepository.delete(mbtiResult);

        return new MBTIResponseDTO.MBTIResultDTO(mbtiType.toString(), result);
    }
}
