package company.gonggam.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
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

    // Kakao Token
    public record KakaoTokenDTO(
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("token_type")
            String tokenType,
            @JsonProperty("refresh_token")
            String refreshToken,
            @JsonProperty("id_token")
            String idToken,
            @JsonProperty("expires_in")
            int expiresIn,
            String scope,
            @JsonProperty("refresh_token_expires_in")
            int refreshTokenExpiresIn
    ) {
    }

    // Kakao Info
    public record KakaoInfoDTO(
            long id,
            @JsonProperty("has_signed_up")
            boolean hasSignedUp,
            @JsonProperty("connected_at")
            LocalDateTime connectedAt,
            KakaoProperties properties,
            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount
    ) {
        public record KakaoProperties(
                String nickname
        ) {
        }

        public record KakaoAccount(
                @JsonProperty("profile_nickname_needs_agreement")
                boolean profileNicknameNeedsAgreement,
                String nickName,
                @JsonProperty("email_needs_agreement")
                boolean emailNeedsAgreement,
                @JsonProperty("is_email_valid")
                boolean isEmailValid,
                @JsonProperty("is_email_verified")
                boolean isEmailVerified,
                String email,
                @JsonProperty("age_range_needs_agreement")
                boolean ageRangeNeedsAgreement,
                @JsonProperty("age_range")
                String age_range,
                @JsonProperty("age_range_needs_agreement")
                boolean genderNeedsAgreement,
                String gender
        ) {
        }
    }
}
