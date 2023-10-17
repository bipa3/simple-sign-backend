package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ApprovalLineDetailListDTO {

    private int userId;
    private String user;
    private int deptId;
    private String department;
    private int estId;
    private String establishment;
    private int compId;
    private String company;
    private char category = 'U';
    private String position;
    private String grade;
    private int approvalOrder;
    private char approvalStatus;

}
