package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrgMapper {

    // TreeView : 회사 - 사업장 - 상위 부서 -하위 부서
    List<OrgCompanyDTO> selectOrgTreeView();

}
