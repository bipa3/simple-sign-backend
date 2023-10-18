package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgRespDTO {
    private int compId;
    private int estId;
    private int deptId;
    private String compName;
    private String estName;
    private String deptName;
    private int positionCode;
    private int gradeCode;
    private int orgUserId;
    private String positionName;
    private String gradeName;
    private String userName;
}