package bitedu.bipa.simplesignbackend.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApprovalEvent {

    private final int approvalDocId;
    private final int receiverId;
    private final String alarmCode;
    private int approverId;


    public ApprovalEvent(int approvalDocId, int receiverId, String alarmCode, int approverId) {
        this.approvalDocId = approvalDocId;
        this.receiverId = receiverId;
        this.alarmCode = alarmCode;
        this.approverId = approverId;
    }


}
