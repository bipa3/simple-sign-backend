package bitedu.bipa.simplesignbackend.enums;

public enum AlarmStatus {
    SUBMIT("01"),
    APPROVE("02"),
    RETURN("03"),
    REPLY("04"),
    RECEIVEDREF("05"),
    UPDATE("06"),
    UPDATE_REPLY("07"),
    APPROVAL_CANCEL("08"),
    APPROVAL_CANCEL_UPPER_APPROVER("09");

    private final String code;

    AlarmStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
