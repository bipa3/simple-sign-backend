package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class SeqDetailDTO {
    @NotNull(message = "회사 아이디가 유효하지 않습니다.")
    private String compId;
    private String code;
    @NotNull(message = "채번명이 유효하지 않습니다.")
    @Size(max=20, message = "채번명이 유효하지 않습니다.")
    private String seqName;
    @Size(max=100, message = "사용부서는 최대 100개의 데이터만 입력 가능합니다.")
    private List<SeqScopeDTO> deptScope;
    @Size(max=100, message = "사용양식은 최대 100개의 데이터만 입력 가능합니다.")
    private List<SeqScopeDTO> formScope;
    @Size(max=100, message = "설명은 최대 100자까지 입력 가능합니다.")
    private String description;
    @Min(value = 0, message = "이 유효하지 않습니다.")
    @Max(value = 127, message = "정렬값은 127을 초과할 수 없습니다.")
    private int sortOrder;
    private String seqList;
    @Size(max=80, message = "채번값이 유효하지 않습니다.")
    private String seqString;
}

