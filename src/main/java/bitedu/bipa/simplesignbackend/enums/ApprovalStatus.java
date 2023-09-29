package bitedu.bipa.simplesignbackend.enums;

public enum ApprovalStatus {
    WAIT('W'),      // 대기
    PROGRESS('P'),  // 진행
    APPROVAL('A'),  // 승인
    TEMPORARY('T'), // 임시저장
    RETURN('R'),    // 반려
    DELETE('D');    // 삭제

    private final char code;

    ApprovalStatus(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}


