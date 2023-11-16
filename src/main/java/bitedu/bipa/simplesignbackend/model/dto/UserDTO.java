package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String userName;
    @Pattern(regexp = "^(010)-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phone;
    @Size(min = 1, max = 100, message = "주소는 1자 이상 100자 이하로 입력해주세요.")
    private String address;
    private String password;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    private char gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birth;
    private boolean employmentStatus;
    private String loginId;
    private String employeeNumber;

    // 비밀번호 암호화
    private String salt;

    //개인정보
    private int orgUserId;
    private String compName;
    private String estName;
    private String deptString;
    private String positionName;
    private String gradeName;

    // 프로필, 서명 이미지 조회 및 수정 + 업로드
    private String approvalFilePath;
    private boolean signState;

    // 겸직 정보
    private List<UserOrgDTO> userOrgList;
}
