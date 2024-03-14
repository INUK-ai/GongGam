package company.gonggam._core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GONGGAM-000", "Internal server error"),
    EMPTY_EMAIL_MEMBER(HttpStatus.NOT_FOUND, "USER-001", "해당 이메일은 회원 가입 되지 않은 이메일입니다."),
    SAME_EMAIL(HttpStatus.CONFLICT, "USER-002", "이미 가입된 이메일입니다."),
    EMAIL_STRUCTURE(HttpStatus.FORBIDDEN,"USER-003","이메일 형식으로 작성해주세요"),
    INVALID_PASSWORD(HttpStatus.CONFLICT, "USER-004", "비밀번호가 유효하지 않습니다."),
    INVALID_EMAIL_CODE(HttpStatus.CONFLICT, "USER-005", "이메일 인증 코드가 일치하지 않습니다."),
    INVALID_EMAIL(HttpStatus.CONFLICT, "USER-006", "이메일이 인증되지 않았습니다."),
    EMAIL_NON_EXIST(HttpStatus.CONFLICT, "USER-007", "해당 이메일에 해당하는 회원이 없습니다."),
    NO_SUCH_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL-001", "사용 가능한 암호화 알고리즘을 찾을 수 없습니다."),
    FAILED_GET_ACCESS_TOKEN(HttpStatus.EXPECTATION_FAILED, "SOCIAL-001", "Access Token 을 가져오는데 실패했습니다."),
    FAILED_GET_KAKAO_PROFILE(HttpStatus.EXPECTATION_FAILED, "SOCIAL-002", "Kakao 유저 프로필을 가져오는데 실패했습니다.");

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
