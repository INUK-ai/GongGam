package company.gonggam.member.domain;

import company.gonggam.BaseTimeEntity;
import company.gonggam.mascot.domain.MemberMascot;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_tb")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_mascot_id")
    private MemberMascot mainMascot;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMascot> memberMascotList = new ArrayList<>();

    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private AgeGroup ageGroup;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private SocialType socialType;
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private Authority authority;

    @Builder
    public Member(Long id, String name, String email, String password, Gender gender, AgeGroup ageGroup, SocialType socialType, Authority authority) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.socialType = socialType;
        this.authority = authority;
    }

    public void addMemberMascot(MemberMascot memberMascot) {
        this.memberMascotList.add(memberMascot);
        memberMascot.setMember(this);
    }
}
