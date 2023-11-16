package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.mapper.UserMapper;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public UserDTO detailUser(int orgUserId){
        return userMapper.selectUser(orgUserId);
    }

    //개인정보 수정
    public boolean updateUser(UserDTO userDTO){
        return userMapper.updateUser(userDTO);
    }

    // 프로필 조회
    public String getUserProfile(int userId){
        return userMapper.selectProfile(userId);
    }

    // 프로필 삽입
    public boolean insertProfile(int userId, String approvalFilePath){
        return userMapper.insertProfile(userId, approvalFilePath);
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

    // 사인 수정
    public boolean updateSignState(int userId, boolean signState){
        return userMapper.updateSignState(userId,signState);
    }
    public boolean insertSign(int userId, String approvalFilePath){
        return userMapper.insertSign(userId, approvalFilePath);
    }
    public boolean updateSign(int userId, String approvalFilePath){
        return userMapper.updateSign(userId, approvalFilePath);
    }

    public List<UserOrgDTO> getOrgList(int userId) {
        return userMapper.getOrgList(userId);
    }

    public int selectPasswordByInput(int userId, String currentPwHash) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", currentPwHash);
        return userMapper.selectPasswordByInput(map);
    }

    public int getUserIdByOrgUserId(int approverId) {
        return userMapper.selectUserIdByOrgUserId(approverId);
    }

    public int getSignFileIdByUserId(int userId) {
        return userMapper.selectSignFileIdByUserId(userId);
    }

    public String getSignImageBySignFileId(int signFileId) {
        return userMapper.selectSignImageBySignFileId(signFileId);
    }
}
