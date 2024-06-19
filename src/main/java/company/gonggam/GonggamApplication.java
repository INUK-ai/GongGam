package company.gonggam;

import company.gonggam.mbti.domain.MBTIQuestion;
import company.gonggam.mbti.repository.MBTIRepository;
import company.gonggam.member.domain.*;
import company.gonggam.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GonggamApplication {

	private final PasswordEncoder passwordEncoder;

	public GonggamApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(GonggamApplication.class, args);
	}

	@Bean
	@Profile("local")
	protected CommandLineRunner localServerStart(MemberRepository memberRepository, MBTIRepository mbtiRepository) {
		return args -> {
			memberRepository.saveAll(List.of(
					newMember("황인욱", "dragonica123@naver.com", "arheon2197!", "male", "20~29")
			));

			mbtiRepository.saveAll(List.of(
					newMBTIQuestion("IE", "주로 혼자 시간을 보낼 때 휴식이 된다고 생각하십니까?", -10),
					newMBTIQuestion("NS", "명상을 할 때 다양한 상상을 하시는 편이십니까?", -10),
					newMBTIQuestion("FT", "영화나 독서를 할 때 등장인물에 감정 이입이 잘 되는 편이십니까?", -10),
					newMBTIQuestion("PJ", "여행계획은 주로 자신이 계획하는 편이십니까?", 10)
			));
		};
	}

	private Member newMember(String name, String email, String password, String gender, String age_range) {
		return Member.builder()
				.name(name)
				.email(email)
				.password(passwordEncoder.encode(password))
				.gender(Gender.fromString(gender))
				.ageGroup(AgeGroup.fromString(age_range))
				.socialType(SocialType.NONE)
				.authority(Authority.USER)
				.build();
	}

	private MBTIQuestion newMBTIQuestion(String type, String question, int bias) {
		return MBTIQuestion.builder()
				.type(type)
				.question(question)
				.bias(bias)
				.build();
	}
}
