package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.service.AuthorityService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/authority")
    public ResponseEntity<Integer> authorityCodeSelect(@RequestParam int orgUserId){
        int authorityCodeService = authorityService.selectAuthorityCode(orgUserId);
        if(authorityCodeService != 0){
            SessionUtils.addAttribute("authorityCode", authorityCodeService);
        }
        return ResponseEntity.ok(authorityCodeService);
    }
}
