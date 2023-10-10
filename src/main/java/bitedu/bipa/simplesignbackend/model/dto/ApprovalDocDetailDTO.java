package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApprovalDocDetailDTO {
    private int approvalDocId;
    private int orgUserId;
    private String userName;
    private String deptName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime approvalDate;
    private String approvalDocTitle;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime enforcementDate;
    private String productNum;
    private int formCode;
    private String defaultForm;
    private List<ApprovalLineListDTO> approvalLineList;
    private List<ReceivedRefListDTO> receivedRefList;
}
