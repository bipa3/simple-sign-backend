package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import bitedu.bipa.simplesignbackend.service.AuthorityService;
import bitedu.bipa.simplesignbackend.service.UserService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/authority")
    public ResponseEntity<List<UserOrgDTO>> getUserAuthority(){
        int userId = (int) SessionUtils.getAttribute("userId");
        List<UserOrgDTO> userOrgList = userService.orgUser(userId);

        return ResponseEntity.ok(userOrgList);
    }

    @GetMapping("/authority")
    public ResponseEntity<UserOrgDTO> authorityCodeSelect(@RequestParam int orgUserId){
        UserOrgDTO userOrgDTO = authorityService.selectAuthorityCode(orgUserId);
        if(userOrgDTO != null){
            SessionUtils.addAttribute("orgUserId", userOrgDTO.getOrgUserId());
            SessionUtils.addAttribute("compId", userOrgDTO.getCompId());
            SessionUtils.addAttribute("compName", userOrgDTO.getCompName());
            SessionUtils.addAttribute("deptId", userOrgDTO.getDeptId());
            SessionUtils.addAttribute("deptName", userOrgDTO.getDeptName());
            SessionUtils.addAttribute("authorityCode", userOrgDTO.getAuthorityCode());
            SessionUtils.addAttribute("authorityName", userOrgDTO.getAuthorityName());

        }
        return ResponseEntity.ok(userOrgDTO);
    }
}
