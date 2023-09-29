package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ProductNumberReqDTO {
    private String status;
    private int useId;
    private int seqCode;
}
