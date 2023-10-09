package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.UserDAO;
import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserPasswordDTO;
import bitedu.bipa.simplesignbackend.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserDAO userDAO;

    public UserDTO loginUser(String loginId, String password) {
        // 암호화된 비밀번호 확인
        String currentSalt = userDAO.getLoginSalt(loginId);
        String currentPwHash = PasswordUtil.getEncode(password, currentSalt);

        UserDTO userDTO = userDAO.loginUser(loginId, currentPwHash);
        if (userDTO != null && userDTO.getLoginId().equals(loginId) && userDTO.getPassword().equals(currentPwHash)) {
            return userDTO;
        }
        return null;
    }

    // 비밀번호 암호화 + 변경
    public boolean passwordChange(UserPasswordDTO userPasswordDTO){
        int userId = userPasswordDTO.getUserId();
        // db에 현재 salt와 암호화된 비밀번호 가져오기
        String currentSalt = userDAO.getSalt(userId);
        // 비밀번호 체크
        String currentPassword = userDAO.passwordClick(userId);
        // 현재 비밀번호 암호화하기
        String currentPwHash = PasswordUtil.getEncode(userPasswordDTO.getCurrentPassword(),currentSalt);
        if(!currentPassword.equals(currentPwHash)){
            return false;
        }

        // 비밀번호 변경 + 암호화
        String newPw = userPasswordDTO.getNewPassword();
        String newSalt = PasswordUtil.getSalt();
        String hashPwd = PasswordUtil.getEncode(newPw, newSalt);

        boolean flag = userDAO.passwordUpdate(userId, hashPwd, newSalt);
        if(!flag) {
            return false;
        }

        return true;
    }
}
