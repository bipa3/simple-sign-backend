package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    List<CompanyDTO> getCompanyList(int compId);

    BelongOrganizationDTO getBelongs(int userId);
    int getLastInsertId();

    PositionAndGradeDTO getPositionAndGrade(int userId);

    int selectDeptId(int userId);
    List<SeqItemListDTO> selectSeqItemList();

    int selectCompIdByFormCode(int formCode);
    int selectCompIdBySeqCode(int seqCode);

    List<FormRecommendResDTO> getRecommendedForm(int OrgUserId);

//    List<Member> getMemberList();
//
//    int createMember(Member member);
//
//    int updateMember(Member member);
//
//    int deleteMember(int id);
}
