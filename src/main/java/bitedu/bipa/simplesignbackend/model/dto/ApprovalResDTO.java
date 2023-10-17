package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalResDTO {

    private int approvalId;
    private int approvalDocId;
    private LocalDateTime receiveDate;
    private LocalDateTime approvalDate;
    private int approvalOrder;
    private char approvalStatus;
    private int orgUserId;
    private String gradeName;
    private String positionName;
}
