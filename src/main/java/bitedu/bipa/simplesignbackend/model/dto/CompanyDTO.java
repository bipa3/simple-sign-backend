package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyDTO implements Serializable {
    private int id;
    private String name;
}
