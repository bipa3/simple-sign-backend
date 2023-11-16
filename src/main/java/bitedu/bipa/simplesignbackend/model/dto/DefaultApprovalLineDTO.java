package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class DefaultApprovalLineDTO {
    private int compId;
    private String compName;
    private int estId;
    private String estName;
    private int deptId;
    private String deptName;
    private int userId;
    private String userName;
    private int lineOrder;
    private int gradeCode;
    private int positionCode;
    private String gradeName;
    private String positionName;
}