package bitedu.bipa.simplesignbackend.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "적절한 값이 입력되지 않았습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부서버 문제가 있습니다."),
    UNEXPECTED_TYPE(HttpStatus.BAD_REQUEST, "적절한 요청이 아닙니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
