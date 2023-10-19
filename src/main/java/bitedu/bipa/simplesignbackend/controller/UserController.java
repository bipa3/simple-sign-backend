package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.UserDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserPasswordDTO;
import bitedu.bipa.simplesignbackend.service.S3Service;
import bitedu.bipa.simplesignbackend.service.UserService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    private final S3Service s3Service;

    @PostMapping("/login")
    public ResponseEntity<Integer> userLogin(@RequestBody UserDTO userDTO, HttpServletResponse response, HttpServletRequest request){
        UserDTO userDTO2 = userService.loginUser(userDTO.getLoginId(),userDTO.getPassword());
        if(userDTO2 != null){

            int userId = userDTO2.getUserId();
            String userName = userDTO2.getUserName();
            SessionUtils.addAttribute("userId", userId);
            SessionUtils.addAttribute("userName", userName);

//            System.out.println("유저 아이디 : " + userId);
//            System.out.println("로그인 " + RequestContextHolder.getRequestAttributes().getSessionId());

            //response.setHeader("Set-Cookie", "JSESSIONID=" + RequestContextHolder.getRequestAttributes().getSessionId() + "; path=/; Secure; SameSite=None");
            response.addHeader("Set-Cookie", "JSESSIONID=" + RequestContextHolder.getRequestAttributes().getSessionId() + "; Path=/; Secure; HttpOnly; SameSite=None");

            return ResponseEntity.ok(userId);
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
        //System.out.println("비밀번호 " + RequestContextHolder.getRequestAttributes().getSessionId());
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
        //System.out.println("개인정보 조회 " + RequestContextHolder.getRequestAttributes().getSessionId());
        int userId = (int) SessionUtils.getAttribute("userId");
        return userService.detailUser(userId);
    }

    // 개인정보 수정
    @PutMapping("/updateinfo")
    public ResponseEntity userUpdate(@RequestBody UserDTO userDTO){

        //System.out.println("개인정보 수정 " + RequestContextHolder.getRequestAttributes().getSessionId());

        int userId = (int) SessionUtils.getAttribute("userId");
        userDTO.setUserId(userId);

        boolean flag = userService.updateUser(userDTO);
        if(flag){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //프로필 조회
    @GetMapping("/userinfo/profile")
    public ResponseEntity<String> userProfileGet(){
        //System.out.println("프로필 조회 " + RequestContextHolder.getRequestAttributes().getSessionId());
        String profile = userService.getUserProfile();
        return ResponseEntity.ok(profile);
    }

    // 사인 조회
    @GetMapping("/userinfo/sign")
    public ResponseEntity<String> userSignGet(){
        //System.out.println("사인 조회 " + RequestContextHolder.getRequestAttributes().getSessionId());
        boolean flag = userService.getSignState();

        if(flag){
            String sign = userService.getSignImage();
            return ResponseEntity.ok(sign);
        }
        return ResponseEntity.ok("default");
    }

    @GetMapping("/updateinfo/sign")
    public ResponseEntity<String> signGet(){
        //System.out.println("사인 수정 " + RequestContextHolder.getRequestAttributes().getSessionId());
        String sign = userService.getSign();
        return ResponseEntity.ok(sign);
    }

    // 프로필 수정
    @PostMapping("/updateinfo/profile")
    public ResponseEntity uploadProfileFile(@RequestParam("file") MultipartFile file) throws IOException {
        //System.out.println("프로필 수정 " + RequestContextHolder.getRequestAttributes().getSessionId());
            String s3Url = s3Service.upload(file, "profile");
            boolean flag = userService.updateProfile(s3Url);
            if(flag){
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // 사인 수정
    @PostMapping("/updateinfo/sign")
    public ResponseEntity<String> uploadSignFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("signState") boolean signState) throws IOException {
        //System.out.println("서명 수정 " + RequestContextHolder.getRequestAttributes().getSessionId());

        if(file == null || file.isEmpty()){
            boolean flag = userService.updateSignState(signState);
            if(flag){
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            String s3Url = s3Service.upload(file, "sign");
            userService.updateSignState(signState);
            userService.updateSign(s3Url);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

}
