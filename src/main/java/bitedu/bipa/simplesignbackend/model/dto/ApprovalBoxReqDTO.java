package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalBoxReqDTO {
    private int approvalBoxId;
    private int compId;
    private String approvalBoxName;
    private ArrayList<String> viewItems;
    private String approvalBoxUsedStatus;
    private String menuUsingRange;
    private ArrayList<BoxUseDepartmentDTO> boxUseDept;
    private int sortOrder;


}
