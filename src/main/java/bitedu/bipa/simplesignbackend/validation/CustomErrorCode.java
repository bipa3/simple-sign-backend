package bitedu.bipa.simplesignbackend.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INACTIVE_USER(HttpStatus.FORBIDDEN, "해당 메뉴를 사용할 수 있는 권한이 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "인증되지 않은 사용자입니다."),
    APPROVALLINE_INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"결재라인 삽입에 실패했습니다."),
    RECEIVED_REF_INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"수신참조 삽입에 실패했습니다."),
    APPROVAL_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결재승인에 실패했습니다."),
    RETURN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"결재반려에 실패했습니다."),
    INAPPROPRIATE_USER(HttpStatus.BAD_REQUEST, "결재할 수 있는 사용자가 아닙니다."),
    ALARM_INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"알람 보내기에 실패했습니다."),
    APPROVAL_DOC_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "문서수정에 실패했습니다."),
    APPROVAL_DOC_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "문서삭제에 실패했습니다."),
    NEXT_APPROVER_APPROVAL_COMPLETE(HttpStatus.BAD_REQUEST, "이미 다음 결재자가 결재를 완료하여 결재취소할 수 없습니다."),
    REPLY_INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결재의견 삽입을 실패했습니다."),
    REPLY_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결재의견 수정에 실패했습니다."),
    REPLY_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결재의견 삭제에 실패했습니다."),
    APPROVAL_DOC_COMPLETED(HttpStatus.BAD_REQUEST, "이미 결재된 문서입니다."),
    APPROVAL_DOC_DELETED(HttpStatus.BAD_REQUEST, "존재하지 않는 문서입니다."),
    INAPPROPIRATE_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    FAVORITE_INSERT_FAIL(HttpStatus.BAD_REQUEST,"즐겨찾기 등록 실패"),
    FAVORITE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "즐겨찾기 삭제 실패"),
    ALARM_SCHEDULING_INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "스케줄링 알람복사 실패"),
    ALARM_SCHEDULING_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "스케줄링 알람삭제 실패")
    //

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
