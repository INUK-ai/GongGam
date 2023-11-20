package company.gonggam.member;

import company.gonggam.question.Answer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_tb")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "")
    private List<Answer> answerList = new ArrayList<>();

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime created_at;

    @Enumerated(value = EnumType.STRING)
    Authority authority;
}
