package company.gonggam.mascot.domain;

import company.gonggam.BaseTimeEntity;
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
@Table(name = "mascot_tb")
public class Mascot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "mascot", cascade = CascadeType.REMOVE)
    private List<MemberMascot> memberMascotList = new ArrayList<>();

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MascotType mascotType;

    @Builder
    public Mascot(String name, MascotType mascotType) {
        this.name = name;
        this.mascotType = mascotType;
    }
}
