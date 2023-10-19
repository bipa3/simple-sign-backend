package bitedu.bipa.simplesignbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String userName;
    private String phone;
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
    private String compName;
    private String estName;
    private String deptString;
    private String positionName;
    private String gradeName;

    // 프로필, 서명 이미지 조회 및 수정 + 업로드
    private String approvalFilePath;
    private boolean signState;
}
