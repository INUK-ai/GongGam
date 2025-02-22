package company.gonggam.answer.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.mascot.domain.MemberMascot;
import company.gonggam.member.domain.Member;
import company.gonggam.question.domain.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer_tb")
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_mascot_id")
    private MemberMascot memberMascot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<AnswerComment> comments = new ArrayList<>();

    @Column(columnDefinition="TEXT")
    private String content;
    @ColumnDefault("false")
    private boolean disclosure;

    @Builder
    public Answer(MemberMascot memberMascot, Question question, String content, boolean disclosure) {
        this.memberMascot = memberMascot;
        this.question = question;
        this.content = content;
        this.disclosure = disclosure;
    }
}
