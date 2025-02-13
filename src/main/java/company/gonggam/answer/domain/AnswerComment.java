package company.gonggam.answer.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.mascot.domain.MemberMascot;
import company.gonggam.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer_comment_tb")
public class AnswerComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mascot_id")
    private MemberMascot memberMascot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Builder
    public AnswerComment(MemberMascot memberMascot, Answer answer, String comment) {
        this.memberMascot = memberMascot;
        this.answer = answer;
        this.comment = comment;
    }

    public void setAnswer(Answer answer) {
        answer.getComments().add(this);
    }
}