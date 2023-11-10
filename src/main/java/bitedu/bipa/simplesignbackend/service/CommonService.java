package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.CommonDTO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormRecommendResDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqItemListDTO;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CommonErrorCode;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    CommonDAO commonDAO;

    public CommonService (CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public ArrayList<CompanyDTO> selectCompList() {
        int authorityCode = (int) SessionUtils.getAttribute("authorityCode");
        int compId = 0;
        if(authorityCode == 2){
            compId = (int) SessionUtils.getAttribute("compId");
        }
        return commonDAO.selectCompany(compId);
    }

    @Cacheable(value="seqItemList")
    public List<SeqItemListDTO> selectSeqItemList() { return commonDAO.selectSeqItemList(); }

    public List<FormRecommendResDTO> selectRecommendedForm() {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<FormRecommendResDTO> recommendedForms = new ArrayList<FormRecommendResDTO>();
        try{
            // 사용자별 추천 양식
            recommendedForms = commonDAO.getRecommendedForm(orgUserId);
            if(recommendedForms.size() < 1){
                //회사별 추천 양식
                int compId = (int) SessionUtils.getAttribute("compId");
                recommendedForms = commonDAO.getRecommendedFormByComp(compId);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return recommendedForms;
    }

    public void checkDeptMasterAthority(int id) {
        int authority = (int)SessionUtils.getAttribute("authorityCode");
        if (authority != 1 && !SessionUtils.hasIdAttribute("compId", id)) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
    }

    public List<CommonDTO> selectApprovalKindList() {
        List<CommonDTO> approvalKindList = new ArrayList<CommonDTO>();
        try{
            approvalKindList = commonDAO.getApprovalKindList();
        }catch(Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return approvalKindList;
    }
}
