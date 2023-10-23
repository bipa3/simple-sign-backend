package bitedu.bipa.simplesignbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SeqScopeDTO {
    @NotNull(message = "데이터의 분류 항목이 누락되었습니다.")
    private String category;
    private int useId;
    private String name;
    private int compId;
    @Size(max=20, message = "회사명이 유효하지 않습니다.")
    private String company;
    private int estId;
    @Size(max=20, message = "사업장명이 유효하지 않습니다.")
    private String establishment;
    private int deptId;
    @Size(max=20, message = "부서명이 유효하지 않습니다.")
    private String department;
    private int userId;
    @Size(max=20, message = "사원명이 유효하지 않습니다.")
    private String user;
    @Size(max=20, message = "직책이 유효하지 않습니다.")
    private String position;
    @Size(max=20, message = "직위이 유효하지 않습니다.")
    private String grade;
}
