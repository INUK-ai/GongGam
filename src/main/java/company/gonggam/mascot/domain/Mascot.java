package company.gonggam.mascot.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.member.domain.Member;
import company.gonggam.question.domain.MascotQuestion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mascot_tb")
public class Mascot extends BaseTimeEntity {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToMany(mappedBy = "mascot", cascade = CascadeType.REMOVE)
    private List<MascotQuestion> dailyQuestionList = new LinkedList<>();

    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column
    private int level;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MascotType mascotType;

    @Builder
    public Mascot(Member member, String name) {
        this.member = member;
        this.name = name;
        this.level = 0;
    }
}
