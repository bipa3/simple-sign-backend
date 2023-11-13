package bitedu.bipa.simplesignbackend.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApprovalEvent {

    private final int approvalDocId;
    private final int receiverId;
    private final String alarmCode;

}
