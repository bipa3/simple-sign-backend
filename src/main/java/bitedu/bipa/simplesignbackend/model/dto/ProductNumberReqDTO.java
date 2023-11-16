package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

@Data
public class ProductNumberReqDTO {
    private String productFullName;
    private int productNum;
    private int seqCode;
}
