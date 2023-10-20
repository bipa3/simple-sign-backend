package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.mapper.UserMapper;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private UserMapper userMapper;

    public UserDTO loginUser(String loginId, String password){
        return userMapper.login(loginId, password);
    }

    public String getLoginSalt(String loginId){
        return userMapper.loginSalt(loginId);
    }

    public String getSalt(int userId){
        return userMapper.selectSalt(userId);
    }

    // 비밀번호 확인
    public String passwordClick(int userId){
        return userMapper.passwordClick(userId);
    }

    // 비밀번호 수정
    public boolean passwordUpdate(int userId, String newPassword, String salt){
        return userMapper.updatePassword(userId, newPassword, salt);
    }

    // 개인정보 조회
    public UserDTO detailUser(int userId){
        return userMapper.selectUser(userId);
    }

    //개인정보 수정
    public boolean updateUser(UserDTO userDTO){
        return userMapper.updateUser(userDTO);
    }

    // 프로필 조회
    public String getUserProfile(int userId){
        return userMapper.selectProfile(userId);
    }

    // 프로필 수정
    public boolean updateProfile(int userId, String approvalFilePath){
        return userMapper.updateProfile(userId, approvalFilePath);
    }

    // 사인 조회
    public boolean getSignstate(int userId){
        return userMapper.selectSignState(userId);
    }
    public String getSignImage(int userId){
        return userMapper.selectSignImage(userId);
    }
    public String getSign(int userId){
        return userMapper.selectSign(userId);
    }

    // 사인 수정
    public boolean updateSignState(int userId, boolean signState){
        return userMapper.updateSignState(userId,signState);
    }
    public boolean updateSign(int userId, String approvalFilePath){
        return userMapper.updateSign(userId, approvalFilePath);
    }

    public List<UserOrgDTO> getOrgList(int userId) {
        return userMapper.getOrgList(userId);
    }
}
