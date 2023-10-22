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

    //회사별 트리뷰
    public List<OrgCompanyDTO> getOrgTreeViewComp(int compId){
        return orgMapper.selectOrgTreeViewComp(compId);
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

    public List<OrgRespDTO> getBottomDeptComp(int compId){
        return orgMapper.selectBottomDeptComp(compId);
    }

    public List<OrgRespDTO> getBottomUserComp(int compId){return orgMapper.selectBottomUserComp(compId);}

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

    // Search
    public List<OrgRespDTO> searchComp(String search){
        return orgMapper.searchComp(search);
    }

    public List<OrgRespDTO> searchEst(String search){
        return orgMapper.searchEst(search);
    }

    public List<OrgRespDTO> searchDept(String search){
        return orgMapper.searchDept(search);
    }

    public List<OrgRespDTO> searchUser(String search){
        return orgMapper.searchUser(search);
    }
}