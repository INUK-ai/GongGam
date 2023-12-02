package company.gonggam.mascot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static company.gonggam.mascot.dto.MascotRequestDTO.*;
import static company.gonggam.mascot.dto.MascotResponseDTO.*;

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

    /*
        일일 질문 확인
        - 해당 Mascot의 MascotQuestion에서 질문 하나 반환
     */
    public getDailyQuestionDTO getDailyQuestion() {

        return new getDailyQuestionDTO();
    }

    /*
        일일 질문 답변
     */
    public void answerDailyQuestion(answerDailyQuestionDTO requestDTO) {

    }
}
