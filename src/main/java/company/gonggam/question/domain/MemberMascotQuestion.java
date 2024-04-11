package company.gonggam.question.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.mascot.domain.MemberMascot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_mascot_question_tb")
public class MemberMascotQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mascot_id")
    private MemberMascot memberMascot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public MemberMascotQuestion(MemberMascot memberMascot, Question question) {
        this.memberMascot = memberMascot;
        this.question = question;
    }
}
