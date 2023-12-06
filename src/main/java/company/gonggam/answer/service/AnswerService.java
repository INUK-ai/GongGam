package company.gonggam.answer.service;

import company.gonggam.answer.repository.AnswerRepository;
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
    
    private final AnswerRepository answerRepository;

    /*
        답변을 한 질문리스트 조회
     */
    public getMyQuestionListDTO getMyQuestionList() {
        
        // 회원 확인
        
        // 해당 회원의 AnswerList 조회
        
        // 해당 AnswerList 에 맞는 QuestionList 조회
        
        // QuestionList 반환
        
        return new getMyQuestionListDTO();
    }

    /*
        답변 상세 조회
     */
    public getMyAnswerDTO getMyAnswer(Long answerId) {

        // 회원 확인

        // 해당 Answer 조회

        // 해당 Answer 의 Comment 존재 여부 확인
        
        return new getMyAnswerDTO();
    }

    /*
        답변 공개 여부 수정
     */
    public void changeDisclosurePolicy(Long answerId, changeDisclosurePolicyDTO requestDTO) {

        // 회원 확인
        
        // 해당 Answer 조회
        
        // 답변 공개 여부 수정
        
        // 저장
    }

    /*
        답변에 대한 현재 생각 추가
     */
    public void addComment(Long answerId, addCommentDTO requestDTO) {

        // 회원 확인

        // 해당 Answer 조회

        // AnswerComment 생성

        // 저장
    }

    /*
        해당 질문에 대한 다른 회원의 답변 조회
     */
    public getOtherAnswerListDTO getOtherAnswerList(Long answerId) {

        // 회원 확인

        // 해당 Answer 조회

        // 해당 Question 조회

        // 해당 Question 을 가지고 있는 AnswerList 확인

        return new getOtherAnswerListDTO();
    }
}
