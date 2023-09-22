package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.UserDAO;
import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserDAO userDAO;

    public UserDTO loginUser(String loginId, String password){
        UserDTO userDTO = userDAO.loginUser(loginId, password);
        if(userDTO != null && userDTO.getLoginId().equals(loginId) && userDTO.getPassword().equals(password)){
            return userDTO;
        }
        return null;
    }

}
