package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgCompanyDTO {
    private int compId;
    private String compName;
    private int estId;
    private String estName;
    private int deptId;
    private String deptName;
    private int upperDeptId;
    private List<OrgEstablishmentDTO> ests;
}
