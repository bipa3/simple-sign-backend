package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApprovalDocReqDTO {
    //상신시에는 모든 값이 null 값이면 안됨
    private int approvalDocId;
    private int userId;
    private int deptId;
    private String gradeName;
    private String positionName;
    private int formCode;
    private String approvalDocTitle;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int approvalCount;
    private char docStatus;
    private LocalDate enforcementDate;
    private int seqCode;
    private List<Integer> approverList = new ArrayList<>();
    private List<ReceivedRefDTO> receiveRefList = new ArrayList<>();

}
