package company.gonggam.mbti.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mbti_question_tb")
public class MBTIQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String question;
    // bias * request.percent
    @Column(nullable = false)
    private int bias;

    @Builder
    public MBTIQuestion(String type, String question, int bias) {
        this.type = type;
        this.question = question;
        this.bias = bias;
    }
}
