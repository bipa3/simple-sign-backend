package bitedu.bipa.simplesignbackend.enums;

public enum OrganizationStatus {

    COMPANY("C"),
    ESTABLISHMENT("E"),
    DEPARTMENT("D"),
    USER("U");

    private final String code;

    OrganizationStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
