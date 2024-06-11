package company.gonggam.mascot.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam.mascot.domain.Mascot;
import company.gonggam.mascot.domain.MemberMascot;
import company.gonggam.mascot.dto.MascotRequestDTO;
import company.gonggam.mascot.repository.mascot.MascotRepository;
import company.gonggam.mascot.repository.memberMascot.MemberMascotRepository;
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

    private final MascotRepository mascotRepository;
    private final MemberRepository memberRepository;
    private final MemberMascotRepository memberMascotRepository;

    /*
        Member Mascot 생성
     */
    @Transactional
    public void initMascot(MascotRequestDTO.initMascotDTO requestDTO, Long currentMemberId) {

        Member member = getMemberById(currentMemberId);
        Mascot mascot = getMascotByType(requestDTO);

        MemberMascot memberMascot = createMemberMascot(requestDTO, member, mascot);
        memberMascotRepository.save(memberMascot);

        member.getMemberMascotList().add(memberMascot);
        mascot.getMemberMascotList().add(memberMascot);
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

    protected Mascot getMascotByType(MascotRequestDTO.initMascotDTO requestDTO) {
        return mascotRepository.findByMascotType(requestDTO.mbtiType())
                .orElseThrow(() -> new ApplicationException(ErrorCode.FAILED_GET_MASCOT_BY_TYPE));
    }

    protected MemberMascot createMemberMascot(MascotRequestDTO.initMascotDTO requestDTO, Member member, Mascot mascot) {
        return MemberMascot.builder()
                .member(member)
                .mascot(mascot)
                .name(requestDTO.mascotName())
                .build();
    }
}
