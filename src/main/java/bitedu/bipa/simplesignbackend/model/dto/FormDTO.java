package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class FormDTO {
    private String id;
    @Size(max=20, message = "양식명이 유효하지 않습니다.")
    private String formName;
}
