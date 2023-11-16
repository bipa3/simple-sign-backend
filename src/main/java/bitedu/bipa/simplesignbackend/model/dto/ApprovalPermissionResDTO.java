package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApprovalPermissionResDTO {

    private int orgUserId;
    private char approvalStatus;
    private int approvalOrder;
}
