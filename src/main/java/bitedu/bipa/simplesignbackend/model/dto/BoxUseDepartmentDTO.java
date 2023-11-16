package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxUseDepartmentDTO {
    private String category;
    private int useId;
    private int compId;
    private String company;
    private int estId;
    private String establishment;
    private int deptId;
    private String department;
    private int userId;
    private String user;

}
