package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class FormItemDTO implements Serializable {
    private int formListCode;
    @Size(max=20, message = "양식항목이 유효하지 않습니다.")
    private String formListName;
    private String formListTag;
}
