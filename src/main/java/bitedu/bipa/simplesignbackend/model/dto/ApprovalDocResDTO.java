package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalDocResDTO {

    private int approvalDocId;
    private int approvalCount;
    private char docStatus;
    private String productNum;
    private int seqCode;
    private int orgUserId;
    private LocalDateTime endAt;
}
