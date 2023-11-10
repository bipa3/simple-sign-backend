package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgEstablishmentDTO implements Serializable {
    private int estId;
    private String estName;
    private List<OrgDepartmentDTO> depts;
}
