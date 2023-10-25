package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserDTO login(@Param("loginId") String loginId, @Param("password") String password);

    String loginSalt(@Param("loginId") String loginId);

    String selectSalt(int userId);

    // 비밀번호 확인
    String passwordClick(int userId);

    // 비밀번호 수정
    boolean updatePassword(@Param("userId") int userId, @Param("newPassword") String newPassword, @Param("salt") String salt);

    // 개인정보 조회
    UserDTO selectUser(int orgUserId);

    // 개인정보 수정
    boolean updateUser(UserDTO userDTO);

    // 프로필 조회
    String selectProfile(int orgUserId);
    // 프로필 삽입
    boolean insertProfile(@Param("userId") int userId, @Param("approvalFilePath") String approvalFilePath);
    // 프로필 수정
    boolean updateProfile(@Param("userId") int userId, @Param("approvalFilePath") String approvalFilePath);

    // 사인 조회
    boolean selectSignState(int userId);
    String selectSignImage(int userId);

    // 사인 이미지 수정
    boolean updateSignState(@Param("userId") int userId, @Param("signState") boolean signState);
    boolean insertSign(@Param("userId") int userId, @Param("approvalFilePath") String approvalFilePath);
    boolean updateSign(@Param("userId") int userId, @Param("approvalFilePath") String approvalFilePath);

    List<UserOrgDTO> getOrgList(int userId);

    int selectPasswordByInput(Map<String, Object> map);
}
