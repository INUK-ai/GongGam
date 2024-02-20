package company.gonggam.util;

import company.gonggam.member.domain.AgeGroup;
import company.gonggam.member.domain.Authority;
import company.gonggam.member.domain.Gender;
import company.gonggam.member.domain.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

public class DummyEntity {

    protected Member newMember(String name, String email, String gender, Integer ageGroup, Authority authority) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode("test1234"))
                .gender(Gender.fromString(gender))
                .ageGroup(AgeGroup.fromInt(ageGroup))
                .authority(authority)
                .build();
    }

    protected List<Member> memberDummyList() {
        return Arrays.asList(
                newMember("test1", "test1@test.com", "",11, Authority.USER),
                newMember("test2", "test2@test.com", "male",21, Authority.USER),
                newMember("test3", "test3@test.com", "female",31, Authority.USER)
        );
    }
}
