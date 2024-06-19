package company.gonggam.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "mbti", timeToLive = 10800) // 60 * 60 * 3
public class MBTIResult {

    @Id
    private Long id;
    private Map<String, Integer> scores = new HashMap<>(); // 각 MBTI 유형의 점수를 저장할 맵
    private Map<String, Integer> total_bias = new HashMap<>();

    @Builder
    public MBTIResult(Long id, Map<String, Integer> scores, Map<String, Integer> total_bias) {
        this.id = id;
        this.scores = Map.copyOf(scores);
        this.total_bias = Map.copyOf(total_bias);
    }
}
