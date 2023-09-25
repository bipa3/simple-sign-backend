package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.BelongOrganizationDTO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.PositionAndGradeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    List<CompanyDTO> getCompanyList();

    BelongOrganizationDTO getBelongs(int userId);

    PositionAndGradeDTO getPositionAndGrade(int userId);

    int selectDeptId(int userId);

//    List<Member> getMemberList();
//
//    int createMember(Member member);
//
//    int updateMember(Member member);
//
//    int deleteMember(int id);
}
