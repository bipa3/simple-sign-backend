package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ApprovalReqDTO {

    private int userId;
    private int deptId;
    private String gradeName;
    private String positionName;
    private int formCode;
    private String approvalDocTitle;
    private String contents;
    private LocalDateTime createdAt;
    private int approvalCount;
    private char docStatus;
    private LocalDate enforcementDate;
}
