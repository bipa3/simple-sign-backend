package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class DefaultApprovalLineDTO {
    private int compId;
    private String compName;
    private int est_id;
    private String est_name;
    private int deptId;
    private String deptName;
    private int userId;
    private String userName;
    private int lineOrder;
}