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

}
