package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeqItemListDTO implements Serializable {
    private String id;
    private String value;
}
