package company.gonggam.redis.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "mbti", timeToLive = 10800) // 60 * 60 * 3
public class MBTIInterimResult {

    @Id
    private String id;
    private Map<String, Integer> scores; // 각 MBTI 유형의 점수를 저장할 맵

    public MBTIInterimResult(String id, Map<String, Integer> scores) {
        this.id = id;
        this.scores = scores;
    }

    // 각 유형의 점수를 업데이트하는 메소드
    public void updateScore(String type, Integer score) {
        this.scores.put(type, score);
    }
}
