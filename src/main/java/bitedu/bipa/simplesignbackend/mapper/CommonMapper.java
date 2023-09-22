package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.BelongOrganizationDTO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    List<CompanyDTO> getCompanyList();

    BelongOrganizationDTO getBelongs(int userId);

//    List<Member> getMemberList();
//
//    int createMember(Member member);
//
//    int updateMember(Member member);
//
//    int deleteMember(int id);
}
