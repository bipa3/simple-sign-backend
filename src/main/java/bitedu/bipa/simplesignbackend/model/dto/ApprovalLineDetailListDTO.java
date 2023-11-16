package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalLineDetailListDTO {

    private int userId;
    private String user;
    private int deptId;
    private String department;
    private int estId;
    private String establishment;
    private int compId;
    private String company;
    private char category = 'U';
    private String position;
    private String grade;
    private int approvalOrder;
    private char approvalStatus;
    private int signState;
    private int signFileId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime approvalDate;

}
