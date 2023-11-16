package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SeqAndCompDTO {
    private int id;
    @NotNull(message = "회사 아이디가 유효하지 않습니다.")
    private String compId;
    @Size(max=20, message = "회사명이 유효하지 않습니다.")
    private String compName;
    @NotNull(message = "채번명이 유효하지 않습니다.")
    @Size(max=20, message = "채번명이 유효하지 않습니다.")
    private String seqName;
    private String code;
    @Size(max=100, message = "설명이 유효하지 않습니다.")
    private String description;
}
