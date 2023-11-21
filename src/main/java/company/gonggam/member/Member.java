package company.gonggam.member;

import company.gonggam.BaseTimeEntity;
import company.gonggam.question.domain.Answer;
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
@Table(name = "member_tb")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Answer> answerList = new ArrayList<>();

    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 20, nullable = false, unique = true)
    private String phoneNumber;
    @Column(length = 100, nullable = false, unique = true)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    @Column(length = 50)
    private Authority authority;

    @Builder
    public Member(Long id, String name, String email, String phoneNumber, String password, Authority authority) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authority = authority;
    }
}
