package company.gonggam.mascot;

import company.gonggam.BaseTimeEntity;
import company.gonggam.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mascot_tb")
public class Mascot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;

    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column
    private int level;
    @Column
    private int experience;

    @Builder
    public Mascot(Long id, Member member, String name) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.level = 0;
        this.experience = 0;
    }
}
