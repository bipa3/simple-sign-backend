package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApprovalCancelReqDTO {

    private int orgUserId;
    private int approvalDocId;
    private char approvalStatus = 'P';
    private LocalDateTime approvalDate = null;
    private char docStatus;
    private LocalDateTime endAt = null;

    public ApprovalCancelReqDTO(int orgUserId, int approvalDocId, char docStatus) {
        this.orgUserId = orgUserId;
        this.approvalDocId = approvalDocId;
        this.docStatus = docStatus;
    }

}
