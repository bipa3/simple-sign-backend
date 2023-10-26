package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalBoxReqDTO {
    private int approvalBoxId;
    @Min(message = "회사 선택은 필수입니다.",value = 1)
    private int compId;
    @NotBlank(message = "명칭을 입력해 주세요.")
    @Size(max = 50, message = "명칭 최대 길이는 50입니다.")
    private String approvalBoxName;
    @NotEmpty(message = "조회항목을 선택해 주세요.")
    private ArrayList<String> viewItems;
    @NotBlank(message = "사용여부를 선택해 주세요")
    private String approvalBoxUsedStatus;
    @NotBlank(message = "사용범위를 선택해 주세요")
    private String menuUsingRange;
    private ArrayList<BoxUseDepartmentDTO> boxUseDept;
    private int sortOrder;


}
