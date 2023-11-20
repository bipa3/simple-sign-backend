package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class SequenceListDTO {

    private int seqCode;
    private String name;
    private char status;
    private int useId;
}
