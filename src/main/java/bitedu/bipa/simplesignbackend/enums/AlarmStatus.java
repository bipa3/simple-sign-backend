package bitedu.bipa.simplesignbackend.enums;

public enum AlarmStatus {
    Submit("01"),
    Approve("02"),
    Return("03"),
    Reply("04"),
    ReceivedRef("05"),
    Update("06");

    private final String code;

    AlarmStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
