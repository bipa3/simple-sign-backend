package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.OrgMapper;
import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgDAO {

    @Autowired
    private OrgMapper orgMapper;

    //회사 - 사업장 - 상위부서 - 하위부서
    public List<OrgCompanyDTO> getOrgTreeView(){
        return orgMapper.selectOrgTreeView();
    }

}
