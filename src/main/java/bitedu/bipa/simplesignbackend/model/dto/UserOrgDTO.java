package bitedu.bipa.simplesignbackend.model.dto;
import lombok.Data;
@Data
public class UserOrgDTO {
    private int orgUserId;
    private int compId;
    private String compName;
    private int deptId;
    private String deptName;
    private int authorityCode;
    private String authorityName;
}
