package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalBoxDetailDTO {
    private int approvalBoxId;
    private String approvalBoxName;
    private int sortOrder;
    private int approvalBoxUsedStatus;
    private char menuUsingRange;
    private int boxDeleteStatus;
    private int compId;
    private String compName;

}