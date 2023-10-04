package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class FormAndCompDTO {
    private int id;
    private String compId;
    private String compName;
    private String formName;
    private int status;
}
