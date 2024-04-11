package company.gonggam.mascot.domain;


import company.gonggam.BaseTimeEntity;
import company.gonggam.answer.domain.Answer;
import company.gonggam.member.domain.Member;
import company.gonggam.question.domain.MemberMascotQuestion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_mascot_tb")
public class MemberMascot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascot_id")
    private Mascot mascot;
    @OneToMany(mappedBy = "memberMascot", cascade = CascadeType.REMOVE)
    private List<MemberMascotQuestion> dailyQuestionList = new ArrayList<>();
    @OneToMany(mappedBy = "memberMascot")
    private List<Answer> answerList = new ArrayList<>();

    @Column
    private String name;
    @Column
    private int level;
    // Client가 가지고 있다가 접속할 때 주면 안되나?
    @Column
    private int questionIndex;

    @Builder
    public MemberMascot(Member member, Mascot mascot, String name) {
        this.member = member;
        this.mascot = mascot;
        this.name = name;
        this.level = 0;
        this.questionIndex = 0;
    }
}
