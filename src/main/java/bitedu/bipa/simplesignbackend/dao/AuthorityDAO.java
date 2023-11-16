package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.AuthorityMapper;
import bitedu.bipa.simplesignbackend.model.dto.UserOrgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorityDAO {

    @Autowired
    private AuthorityMapper authorityMapper;

    public UserOrgDTO selectAuthorityCode(int orgUserId){
        return authorityMapper.selectAuthorityCode(orgUserId);
    }
}
