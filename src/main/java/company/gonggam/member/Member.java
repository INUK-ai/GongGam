package company.gonggam.member;

import company.gonggam.article.Article;
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

    @OneToMany(mappedBy = "article")
    private List<Article> articleList = new ArrayList<>();

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime created_at;
}
