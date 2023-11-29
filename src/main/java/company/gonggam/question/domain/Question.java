package company.gonggam.question.domain;

import company.gonggam.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_tb")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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