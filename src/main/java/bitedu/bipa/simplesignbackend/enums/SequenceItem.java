package bitedu.bipa.simplesignbackend.enums;

public enum SequenceItem {

    INPUT("01", "직접입력"), //직접입력
    JE("02","제"), //제
    HO("03","호"), //호
    HYPHEN("04","-"), // -
    SLASH("05","/"), // /
    PERIOD("06","."), // .
    COMMA("07",","), // ,
    TILDE("08","~"), // ~
    YEAR("09","년도"), // 년도
    MONTH("10","월"), //월
    DAY("11","일"), //일
    CODE2("12","자릿수 2자리"), //자릿수 2자리
    CODE3("13","자릿수 3자리"), //자릿수 3자리
    CODE4("14","자릿수 4자리"), //자릿수 4자리
    CODE5("15","자릿수 5자리"), //자릿수 5자리
    COMPNAME("16","회사명"), //회사명
    ESTNAME("17","사업장명"), //사업장명
    DEPTNAME("18","부서명"); //부서명


    private final String code;
    private final String productItem;

    SequenceItem(String code, String productItem) {
        this.code = code;
        this.productItem = productItem;
    }
    public String getCode() {
        return code;
    }

    public String getProductItem() {
        return productItem;
    }
}
