package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApprovalDocReqDTO {
    //상신시에는 모든 값이 null 값이면 안됨
    private int approvalDocId;
    private int orgUserId;
    private String gradeName;
    private String positionName;
    private int formCode;
    private String formName;
    @NotEmpty(message = "제목은 필수입니다.")
    private String approvalDocTitle;
    private String contents = "";
    private String searchContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvalDate;
    private int approvalCount;
    private char docStatus;
    private LocalDateTime enforcementDate;
    @Min(message = "품의번호는 빈 값일 수 없습니다.", value = 1)
    private int seqCode;
    @Valid
    @Size(min = 1, message = "결재라인을 입력해주세요.")
    private List<Integer> approverList = new ArrayList<>();
    private List<ReceivedRefDTO> receiveRefList = new ArrayList<>();
    private int version;

}
