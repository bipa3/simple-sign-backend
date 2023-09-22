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

}
