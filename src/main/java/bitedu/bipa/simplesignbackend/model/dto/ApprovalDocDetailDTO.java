package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApprovalDocDetailDTO {
    private int approvalDocId;
    private int orgUserId;
    private String userName;
    private String deptName;
    private LocalDateTime createdAt;
    private String approvalDocTitle;
    private String contents;
    private LocalDate enforcementDate;
    private String productNum;
    private int formCode;
    private String defaultForm;
    private List<ApprovalLineListDTO> approvalLineList;
    private List<ReceivedRefListDTO> receivedRefList;
}
