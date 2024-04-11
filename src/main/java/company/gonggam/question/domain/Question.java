package company.gonggam.question.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.answer.domain.Answer;
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
@Table(name = "question_tb")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    @Column(columnDefinition="TEXT")
    private String content;
    @Column
    private int level;

    @Builder
    public Question(Long id, String content, int level) {
        this.id = id;
        this.content = content;
        this.level = level;
    }
}