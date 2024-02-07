package company.gonggam.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MemberResponseDTO {

    // 토큰 발급
    public record authTokenDTO(
            String grantType,
            String accessToken,
            @JsonFormat(timezone = "Asia/Seoul")
            Date accessTokenValidTime
    ) {

    }
}
