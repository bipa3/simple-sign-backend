package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class FormListDTO {

    private int formCode;
    private String formName;
    private String formExplain;
    private String formApprovalKind;
}
