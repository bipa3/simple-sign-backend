package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalLineDTO {

    private int approvalDocId;
    private int approvalOrder;
    private char approvalStatus;
    private int userId;
    private int deptId;
    private String gradeName;
    private String positionName;
    private LocalDateTime receiveDate;
    private LocalDateTime approvalDate;
}
