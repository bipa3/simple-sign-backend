package bitedu.bipa.simplesignbackend.model.dto;

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
    private Date startDate;
    private char gender;
    private Date birth;
    private boolean employmentStatus;
    private String loginId;
    private String employeeNumber;
}
