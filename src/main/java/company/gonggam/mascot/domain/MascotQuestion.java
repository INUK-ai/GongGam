package company.gonggam.mascot.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.question.domain.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mascot_question_tb")
public class MascotQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascot_id")
    private Mascot mascot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public MascotQuestion(Mascot mascot, Question question) {
        this.mascot = mascot;
        this.question = question;
    }
}
