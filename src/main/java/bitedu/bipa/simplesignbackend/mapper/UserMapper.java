package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserDTO login(@Param("loginId") String loginId, @Param("password") String password);

    String loginSalt(@Param("loginId") String loginId);

    String selectSalt(int userId);

    // 비밀번호 확인
    String passwordClick(int userId);

    // 비밀번호 수정
    boolean updatePassword(@Param("userId") int userId, @Param("newPassword") String newPassword, @Param("salt") String salt);
}
