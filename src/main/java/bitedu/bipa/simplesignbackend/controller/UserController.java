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
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserDTO userDTO){
        UserDTO userDTO2 = userService.loginUser(userDTO.getLoginId(),userDTO.getPassword());
        if(userDTO2 != null){

            int userId = userDTO2.getUserId();
            String userName = userDTO2.getUserName();
            SessionUtils.addAttribute("userId", userId);
            SessionUtils.addAttribute("userName", userName);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("userId", userId);
            responseMap.put("userName", userName);

            return ResponseEntity.ok(responseMap);
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

    // 개인정보 조회
    @GetMapping("/userinfo")
    public UserDTO userDetail(){
        int userId = (int) SessionUtils.getAttribute("userId");
        return userService.detailUser(userId);
    }

    // 개인정보 수정
    @PutMapping("/updateinfo")
    public ResponseEntity userUpdate(@RequestBody UserDTO userDTO){

        int userId = (int) SessionUtils.getAttribute("userId");
        userDTO.setUserId(userId);

        boolean flag = userService.updateUser(userDTO);
        if(flag){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
