package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonDAO {

    CommonMapper commonMapper;

    public CommonDAO(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    public BelongOrganizationDTO getBelongs(int userId) {
        return commonMapper.getBelongs(userId);
    }
    @Cacheable(value="selectCompany", key="#compId")
    public ArrayList<CompanyDTO> selectCompany(int compId) {
        ArrayList<CompanyDTO> companyList = (ArrayList) commonMapper.getCompanyList(compId);
        return companyList;
    }

    public PositionAndGradeDTO getPositionAndGrade(int userId) {
        return commonMapper.getPositionAndGrade(userId);
    }

    public int selectDeptId(int userId) {
        return commonMapper.selectDeptId(userId);
    }


    public List<SeqItemListDTO> selectSeqItemList() {
        return commonMapper.selectSeqItemList();
    }

    public int selectCompIdByFormCode(int formCode) {
        return commonMapper.selectCompIdByFormCode(formCode);
    }

    public int selectCompIdBySeqCode(int seqCode) {
        return commonMapper.selectCompIdBySeqCode(seqCode);
    }

    public List<FormRecommendResDTO> getRecommendedForm(int orgUserId) { return commonMapper.getRecommendedForm(orgUserId); }
    public List<FormRecommendResDTO> getRecommendedFormByComp(int compId) { return commonMapper.getRecommendedFormByComp(compId); }

    public List<CommonDTO> getApprovalKindList() {
        return commonMapper.getApprovalKindList();
    }
}
