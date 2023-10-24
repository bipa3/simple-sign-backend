package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.UserDAO;
import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserPasswordDTO;
import bitedu.bipa.simplesignbackend.utils.PasswordUtil;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserDAO userDAO;

    public UserDTO loginUser(String loginId, String password) {
        // 암호화된 비밀번호 확인
        String currentSalt = userDAO.getLoginSalt(loginId);
        String currentPwHash = PasswordUtil.getEncode(password, currentSalt);
        UserDTO userDTO = userDAO.loginUser(loginId, currentPwHash);
        if (userDTO != null) {
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

    // 개인정보 조회
    public UserDTO detailUser(int orgUserId) {
        return userDAO.detailUser(orgUserId);
    }

    // 개인정보 수정
    public boolean updateUser(UserDTO userDTO) {
        return userDAO.updateUser(userDTO);
    }

    // 프로필 조회
    public String getUserProfile(){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.getUserProfile(userId);
    }

    // 프로필 삽입
    public boolean insertProfile(String approvalFilePath){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.insertProfile(userId, approvalFilePath);
    }

    // 프로필 수정
    public boolean updateProfile(String approvalFilePath){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.updateProfile(userId, approvalFilePath);
    }

    // 사인 조회
    public boolean getSignState(){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.getSignstate(userId);
    }
    public String getSignImage(){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.getSignImage(userId);
    }

    // 사인 수정
    public boolean updateSignState(boolean signState){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.updateSignState(userId,signState);
    }
    public boolean insertSign(String approvalFilePath){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.insertSign(userId, approvalFilePath);
    }
    public boolean updateSign(String approvalFilePath){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userDAO.updateSign(userId,approvalFilePath);
    }

    public List<UserOrgDTO> orgUser(int userId) {
        return userDAO.getOrgList(userId);
    }
}
