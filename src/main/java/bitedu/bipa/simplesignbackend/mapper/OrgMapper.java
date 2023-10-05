package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.OrgRespDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrgMapper {

    // TreeView
    List<OrgCompanyDTO> selectOrgTreeView();

    // GridView
    List<OrgRespDTO> selectComp(int compId);

    List<OrgRespDTO> selectDeptEst(int estId);

    List<OrgRespDTO> selectEst(int estId);

    List<OrgRespDTO> selectDept(int deptId);

    List<OrgRespDTO> selectUser(int deptId);

    List<OrgRespDTO> selectBottomComp(int compId);

    List<OrgRespDTO> selectBottomDeptComp(int compId);

    List<OrgRespDTO> selectBottomDeptEst(int estId);

    List<OrgRespDTO> selectBottomUserEst(int estId);

    List<OrgRespDTO> selectBottomDept(int deptId);

    List<OrgRespDTO> selectBottomUser(int deptId);
}