package company.gonggam.mascot.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.mascot.domain.MascotType;
import company.gonggam.mascot.domain.MemberMascot;
import company.gonggam.mascot.dto.MascotRequestDTO;
import company.gonggam.mascot.repository.MemberMascotRepository;
import company.gonggam.member.domain.Member;
import company.gonggam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static company.gonggam.mascot.dto.MascotResponseDTO.MainMascotListDTO;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MascotService {

    private final MemberRepository memberRepository;
    private final MemberMascotRepository memberMascotRepository;

    /*
        Member Mascot 생성
     */
    @Transactional
    public void initMascot(MascotRequestDTO.initMascotDTO requestDTO, Long currentMemberId) {

        Member member = getMemberById(currentMemberId);

        MemberMascot memberMascot = createMemberMascot(requestDTO, member);
        memberMascotRepository.save(memberMascot);

        member.getMemberMascotList().add(memberMascot);
    }

    /*
        TODO: MainMascot 설정
     */
    @Transactional
    public void setMainMascot(Long currentMemberId, Long mainMascotId) {

    }

    /*
        메인 페이지
        - Member Mascot 반환
     */
    public MainMascotListDTO mainMascot(Long currentMemberId) {

        // 회원 확인
        Member member = getMemberById(currentMemberId);

        // 해당 회원의 Mascot 가져오기
        return new MainMascotListDTO(member.getMemberMascotList());
    }

    protected Member getMemberById(Long currentMemberId) {
        return memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.FAILED_GET_MEMBER_BY_ID));
    }

    // TODO: mascotImageUrl
    protected MemberMascot createMemberMascot(MascotRequestDTO.initMascotDTO requestDTO, Member member) {
        return MemberMascot.builder()
                .member(member)
                .mascotType(MascotType.valueOf(requestDTO.mbtiType()))
                .mascotImageUrl("")
                .name(requestDTO.mascotName())
                .build();
    }
}
