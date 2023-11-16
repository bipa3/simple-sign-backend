package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ApprovalLineListDTO {

    private int orgUserId;
    private String userName;
    private int approvalOrder;
    private char approvalStatus;
    private int approvalCount;

}
