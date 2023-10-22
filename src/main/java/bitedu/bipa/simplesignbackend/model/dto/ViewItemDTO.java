package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewItemDTO {
    private int boxId;
    private String boxName;
    private String codeId;
    private String codeValue;
}
