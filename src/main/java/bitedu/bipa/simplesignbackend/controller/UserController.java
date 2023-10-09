package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserPasswordDTO;
import bitedu.bipa.simplesignbackend.service.UserService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
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

            int userId = userDTO2.getUserId();
            String userName = userDTO2.getUserName();
            SessionUtils.addAttribute("userId", userId);
            SessionUtils.addAttribute("userName", userName);

            return new ResponseEntity(HttpStatus.OK);
        } else {
          return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity userLogout (HttpSession session){
        session.invalidate();
        return new ResponseEntity(HttpStatus.OK);
    }

    // 비밀번호 변경 + 암호화
    @PostMapping("/user/password/change")
    public ResponseEntity chanePassword(@RequestBody UserPasswordDTO userPasswordDTO){
        int userId = (int) SessionUtils.getAttribute("userId");
        userPasswordDTO.setUserId(userId);
        boolean flag = userService.passwordChange(userPasswordDTO);
        if(flag){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
