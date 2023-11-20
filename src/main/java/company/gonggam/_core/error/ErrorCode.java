package company.gonggam._core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GONGGAM-000", "Internal server error"),
    SAME_EMAIL(HttpStatus.CONFLICT, "USER-001", "이미 가입한 이메일입니다."),
    EMAIL_STRUCTURE(HttpStatus.FORBIDDEN,"USER-002","이메일 형식으로 작성해주세요");

    /*
        예시)
        SAME_EMAIL(HttpStatus.CONFLICT, "USER-001", "이미 가입한 이메일입니다.")
        EMAIL_STRUCTURE(HttpStatus.FORBIDDEN,"USER-002","이메일 형식으로 작성해주세요")

        이렇게 여기 정의하고 throw new ApplicationException(ErrorCode.SAME_EMAIL); 던지면 됨
    */

    private HttpStatus status;
    private String errorCode;
    private String message;
}
