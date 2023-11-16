package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalBoxDetailDTO {
    private int compId;
    private String compName;
    private int approvalBoxId;
    private String approvalBoxName;
    private int sortOrder;
    private int approvalBoxUsedStatus;
    private String menuUsingRange;
    private int boxDeleteStatus;


}
