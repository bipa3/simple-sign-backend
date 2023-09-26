package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityTestController {
    @Authority(role = Authority.Role.MASTER_ADMIN)
    @GetMapping("/master")
    public String masterOnly() {
        return "Master Admin";
    }

    @Authority(role = Authority.Role.DEPT_ADMIN)
    @GetMapping("/dept")
    public String companyOnly() {
        return "Dept Admin";
    }

    @Authority(role = Authority.Role.USER)
    @GetMapping("/user")
    public String userOnly() {
        return "User";
    }
}
