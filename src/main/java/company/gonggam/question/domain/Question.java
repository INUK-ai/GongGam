package company.gonggam.question.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.answer.domain.Answer;
import company.gonggam.mascot.domain.MascotType;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MascotType mascotType;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column
    private int level;

    @Builder
    public Question(Long id, MascotType mascotType, String content, int level) {
        this.id = id;
        this.mascotType = mascotType;
        this.content = content;
        this.level = level;
    }
}