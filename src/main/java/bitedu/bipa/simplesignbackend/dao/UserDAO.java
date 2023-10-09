package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
