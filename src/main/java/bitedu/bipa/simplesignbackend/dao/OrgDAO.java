package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.OrgMapper;
import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.OrgRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgDAO {

    @Autowired
    private OrgMapper orgMapper;

    //TreeView
    public List<OrgCompanyDTO> getOrgTreeView(){
        return orgMapper.selectOrgTreeView();
    }

    // GridView
    public List<OrgRespDTO> getComp(int compId){
        return orgMapper.selectComp(compId);
    }

    public List<OrgRespDTO> getDeptEst(int estId){return orgMapper.selectDeptEst(estId);}

    public List<OrgRespDTO> getEst(int estId){
        return orgMapper.selectEst(estId);
    }

    public List<OrgRespDTO> getDept(int deptId){
        return orgMapper.selectDept(deptId);
    }

    public List<OrgRespDTO> getUser(int deptId){
        return orgMapper.selectUser(deptId);
    }

    public List<OrgRespDTO> getBottomComp(int compId){
        return orgMapper.selectBottomComp(compId);
    }

    public List<OrgRespDTO> getBottomDeptComp(int compId){return orgMapper.selectBottomDeptComp(compId);}

    public List<OrgRespDTO> getBottomEst(int estId){
        return orgMapper.selectBottomDeptEst(estId);
    }

    public List<OrgRespDTO> getBottomUserEst(int estId){
        return orgMapper.selectBottomUserEst(estId);
    }

    public List<OrgRespDTO> getBottonDept(int deptId){
        return orgMapper.selectBottomDept(deptId);
    }

    public List<OrgRespDTO> getBottonUser(int deptId){
        return orgMapper.selectBottomUser(deptId);
    }
}