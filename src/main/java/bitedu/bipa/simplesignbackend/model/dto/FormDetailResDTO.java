package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class FormDetailResDTO {

    private int code;
    @NotNull(message = "회사 아이디가 유효하지 않습니다.")
    private int compId;
    @NotNull(message = "양식명이 유효하지 않습니다.")
    @Size(max=20, message = "양식명이 유효하지 않습니다.")
    private String formName;
    @Size(max=100, message = "공개범위는 최대 100개의 데이터만 입력 가능합니다.")
    private ArrayList<FormDetailScopeDTO> scope;
    @Size(max=20000, message = "기본 파일의 크기가 초과되었습니다.")
    private String defaultForm;
    @Size(max=20000, message = "본문 파일의 크기가 초과되었습니다.")
    private String mainForm;
    private Boolean status;
    @Size(max=10, message = "결재라인은 최대 10개만 입력 가능합니다.")
    private List<DefaultApprovalLineDTO> approvalLine;
}
