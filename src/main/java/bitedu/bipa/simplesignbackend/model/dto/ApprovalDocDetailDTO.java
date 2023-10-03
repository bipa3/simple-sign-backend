package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ApprovalDocDetailDTO {
    private int approvalDocId;
    private int userId;
    private int deptId;
    private String userName;
    private String deptName;
    private LocalDateTime createdAt;
    private String approvalDocTitle;
    private String contents;
    private LocalDate enforcementDate;
    private String productNum;
    private int formCode;
    private String defaultForm;
}
