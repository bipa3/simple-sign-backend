package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AuthorityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityDAO authorityDAO;

    public int selectAuthorityCode(int orgUserId){
        return authorityDAO.selectAuthorityCode(orgUserId);
    }
}
