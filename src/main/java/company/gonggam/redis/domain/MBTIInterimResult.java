package company.gonggam.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
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
    private Long id;
    private Map<String, Integer> scores; // 각 MBTI 유형의 점수를 저장할 맵
    private Map<String, Integer> total_bias;

    @Builder
    public MBTIInterimResult(Long id, Map<String, Integer> scores, Map<String, Integer> counts) {
        this.id = id;
        this.scores = Map.copyOf(scores);
        this.total_bias = Map.copyOf(counts);
    }

    // 각 유형의 점수를 업데이트하는 메소드
    public MBTIInterimResult updateScore(Map<String, Integer> newScores, Map<String, Integer> newCounts) {
        return MBTIInterimResult.builder()
                .id(this.id)
                .scores(Map.copyOf(newScores))
                .counts(Map.copyOf(newCounts))
                .build();
    }
}
