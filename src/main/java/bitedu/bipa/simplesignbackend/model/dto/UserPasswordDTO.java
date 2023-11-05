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
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+]).{6,15}$",
            message = "비밀번호는 6자 이상 15자 이하이며, 영문(대문자),영문(소문자),숫자,특수문자를 포함해야 합니다.")
    private String newPassword;
}
