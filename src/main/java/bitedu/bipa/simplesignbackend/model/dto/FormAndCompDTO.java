package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class FormAndCompDTO {
    private int id;
    @NotNull(message = "회사 아이디가 유효하지 않습니다.")
    private String compId;
    @Size(max=20, message = "회사명이 유효하지 않습니다.")
    private String compName;
    @NotNull(message = "양식명이 유효하지 않습니다.")
    @Size(max=20, message = "양식명이 유효하지 않습니다.")
    private String formName;
    @Min(value = 0, message = "상태값이 유효하지 않습니다.")
    @Max(value = 1, message = "상태값이 유효하지 않습니다.")
    private int status;
}
