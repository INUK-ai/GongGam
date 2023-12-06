package company.gonggam.answer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static company.gonggam.answer.dto.AnswerRequestDTO.addCommentDTO;
import static company.gonggam.answer.dto.AnswerRequestDTO.changeDisclosurePolicyDTO;
import static company.gonggam.answer.dto.AnswerResponseDTO.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AnswerService {

    /*
        답변을 한 질문리스트 조회
     */
    public getMyQuestionListDTO getMyQuestionList() {
        return new getMyQuestionListDTO();
    }

    /*
        답변 상세 조회
     */
    public getMyAnswerDTO getMyAnswer(Long answerId) {
        return new getMyAnswerDTO();
    }

    /*
        답변 공개 여부 수정
     */
    public void changeDisclosurePolicy(Long answerId, changeDisclosurePolicyDTO requestDTO) {

    }

    /*
        답변에 대한 현재 생각 추가
     */
    public void addComment(Long answerId, addCommentDTO requestDTO) {

    }

    /*
        해당 질문에 대한 다른 회원의 답변 조회
     */
    public getOtherAnswerListDTO getOtherAnswerList(Long answerId) {
        return new getOtherAnswerListDTO();
    }
}
