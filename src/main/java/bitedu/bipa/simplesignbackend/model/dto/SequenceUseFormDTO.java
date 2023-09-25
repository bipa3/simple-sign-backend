package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SequenceUseFormDTO {

    private int seqCode;
    private int formCode;
    private String productFullName;
    private int productNum;
}
