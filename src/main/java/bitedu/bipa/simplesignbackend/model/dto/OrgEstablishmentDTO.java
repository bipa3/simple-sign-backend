package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgEstablishmentDTO {
    private int estId;
    private String estName;
    private List<OrgDepartmentDTO> depts;
}
