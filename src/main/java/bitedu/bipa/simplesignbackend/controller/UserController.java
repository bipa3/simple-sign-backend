package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserDTO userDTO, HttpSession session){
        UserDTO userDTO2 = userService.loginUser(userDTO.getLoginId(),userDTO.getPassword());
        if(userDTO2 != null){

            String userId = String.valueOf(userDTO2.getUserId());
            String userName = userDTO2.getUserName();
            session.setAttribute("userId", userId);
            session.setAttribute("userName", userName);

            String sessionUserId = (String) session.getAttribute("userId");
            String sessionUserName = (String) session.getAttribute("userName");

            return new ResponseEntity(HttpStatus.OK);
        } else {
          return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
