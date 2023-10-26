package bitedu.bipa.simplesignbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    private int userId;
    private String salt;
    @NotEmpty(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 3, max = 8, message = "비밀번호는 3자 이상 8자 이하로 작성해주세요.")
    @Pattern(regexp = "^[a-z](?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+]).*$",
            message = "비밀번호는 첫 글자가 소문자이며, 영어, 숫자, 특수문자를 포함해야 합니다.")
    private String newPassword;
}
