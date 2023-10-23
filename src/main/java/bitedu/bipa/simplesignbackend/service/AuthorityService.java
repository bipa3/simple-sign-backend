package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AuthorityDAO;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityDAO authorityDAO;

    public UserOrgDTO selectAuthorityCode(int orgUserId){
        return authorityDAO.selectAuthorityCode(orgUserId);
    }
}
