package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FormDetailResDTO {
    private int code;
    private int compId;
    private String formName;
    private ArrayList<FormDetailScopeDTO> scope;
    private String defaultForm;
    private String mainForm;
    private Boolean status;
    private List<DefaultApprovalLineDTO> approvalLine;
}
