package bitedu.bipa.simplesignbackend.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INACTIVE_USER(HttpStatus.FORBIDDEN, "해당 메뉴를 사용할 수 있는 권한이 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "인증되지 않은 사용자입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
