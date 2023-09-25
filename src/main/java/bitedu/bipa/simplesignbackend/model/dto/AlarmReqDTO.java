package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmReqDTO {
    private LocalDateTime alarmDate;
    private String alarmCode;
    private int userId;
    private int deptId;
    private int approvalDocId;
    private String positionName;
    private String gradeName;
    private String alarmContent;
}
