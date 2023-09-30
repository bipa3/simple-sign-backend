package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class BelongOrganizationDTO {

    private int userId;
    private int deptId;
    private int estId;
    private int compId;
    private String compName;
    private String estName;
    private String deptName;
}
