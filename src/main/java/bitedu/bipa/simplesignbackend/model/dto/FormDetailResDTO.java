package bitedu.bipa.simplesignbackend.model.dto;

import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FormDetailResDTO {
    private int code;
    private String formName;
    private ArrayList<FormDetailScopeVO> scope;
    private String defaultForm;
    private String mainForm;
    private Boolean status;
}
