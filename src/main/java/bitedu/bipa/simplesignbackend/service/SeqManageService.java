package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.SeqManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqScopeDTO;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CommonErrorCode;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SeqManageService {

    SeqManageDAO seqManageDAO;
    CommonService commonService;

    public SeqManageService (SeqManageDAO seqManageDAO, CommonService commonService) {
        this.seqManageDAO = seqManageDAO;
        this.commonService = commonService;
    }

    public List<SeqAndCompDTO> searchSeqAndCompList(SeqAndCompDTO seqAndCompDTO) {
        commonService.checkDeptMasterAthority(Integer.parseInt(seqAndCompDTO.getCompId()));
        List<SeqAndCompDTO> seqAndCompList = new ArrayList<SeqAndCompDTO>();
        
        seqAndCompList = seqManageDAO.selectSeqAndComp(seqAndCompDTO);
        if(seqAndCompList.size() < 1) {
            throw new RestApiException(CommonErrorCode.NO_CONTENT);
        }
        return seqAndCompList;
    }

    @Transactional
    public SeqDetailDTO searchSeqDetail(int code) {
        SeqDetailDTO seqDetail = new SeqDetailDTO();
        try {
            seqDetail = seqManageDAO.selectSeqDetail(code);
            commonService.checkDeptMasterAthority(Integer.parseInt(seqDetail.getCompId()));

            List<SeqScopeDTO> deptScopeList = seqManageDAO.selectDeptScope(code);
            List<SeqScopeDTO> formScopeList = seqManageDAO.selectFormScope(code);
            seqDetail.setDeptScope(deptScopeList);
            seqDetail.setFormScope(formScopeList);

            List<String> seqItems = List.of(seqDetail.getSeqString().split(","));

            Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("seqItems", seqItems);
            String seqList = seqManageDAO.selectSeqItems(map);

            seqDetail.setSeqList(seqList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return seqDetail;
    }

    public Boolean removeSeq(int code) {
        SeqDetailDTO seqDetail = new SeqDetailDTO();
        try {
            seqDetail = seqManageDAO.selectSeqDetail(code);
            commonService.checkDeptMasterAthority(Integer.parseInt(seqDetail.getCompId()));

            seqManageDAO.deleteSeq(code);
        } catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Transactional
    public Boolean seqDetailRegist(SeqDetailDTO seqDetail) {
        commonService.checkDeptMasterAthority(Integer.parseInt(seqDetail.getCompId()));

        try {
            seqManageDAO.insertSeqDetail(seqDetail);
            int seqCode = seqManageDAO.getSeqDetailId();
            if(seqCode < 1){
                throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
            }

            List<SeqScopeDTO> deptScopeList = seqDetail.getDeptScope();
            for(SeqScopeDTO deptScope : deptScopeList) {
                String category = deptScope.getCategory();
                int userId = deptScope.getUseId();
                if(!category.equals("C") && !category.equals("E") && !category.equals("D") && !category.equals("U")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", category);
                map.put("seqCode", seqCode);
                map.put("useId", userId);
                seqManageDAO.insertSeqDept(map);
            }

            List<SeqScopeDTO> formScopeList = seqDetail.getFormScope();
            for(SeqScopeDTO formScope : formScopeList){
                String category = formScope.getCategory();
                if(!category.equals("F")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                int useId = formScope.getUseId();
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("seqCode", seqCode);
                map.put("formCode", useId);
                seqManageDAO.insertSeqForm(map);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Transactional
    public Boolean seqDetailChange(SeqDetailDTO seqDetailDTO) {
        commonService.checkDeptMasterAthority(Integer.parseInt(seqDetailDTO.getCompId()));

        try {
            int seqCode = Integer.parseInt(seqDetailDTO.getCode());
            seqManageDAO.updateSeqDetail(seqDetailDTO);

            // 회사 scope
            List<SeqScopeDTO> deptScopeList = seqDetailDTO.getDeptScope();
            List<SeqScopeDTO> defaultDeptScopeList = seqManageDAO.getSeqDeptScope(seqCode);
            List<SeqScopeDTO> missingDeptDataList = new ArrayList<>();

            for (SeqScopeDTO defaultDept : defaultDeptScopeList) {
                if (!deptScopeList.contains(defaultDept)) {
                    missingDeptDataList.add(defaultDept);
                }
            }

            for (SeqScopeDTO delDept : missingDeptDataList) {
                String category = delDept.getCategory();
                int userId = delDept.getUseId();

                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", category);
                map.put("seqCode", seqCode);
                map.put("useId", userId);
                seqManageDAO.delDeptScope(map);
            }

            for (SeqScopeDTO insertDept : deptScopeList) {
                String category = insertDept.getCategory();
                int userId = insertDept.getUseId();
                if(!category.equals("C") && !category.equals("E") && !category.equals("D") && !category.equals("U")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", category);
                map.put("seqCode", seqCode);
                map.put("useId", userId);
                seqManageDAO.insertIgnoreDeptScope(map);
            }

            // 양식 scope
            List<SeqScopeDTO> formScopeList = seqDetailDTO.getFormScope();
            List<SeqScopeDTO> defaultFormScopeList = seqManageDAO.getSeqFormScope(seqCode);
            List<SeqScopeDTO> missingFormDataList = new ArrayList<>();


            for (SeqScopeDTO defaultForm : defaultFormScopeList) {
                if (!formScopeList.contains(defaultForm)) {
                    missingFormDataList.add(defaultForm);
                }
            }

            for (SeqScopeDTO delForm : missingFormDataList) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("seqCode", seqCode);
                map.put("formCode", delForm.getUseId());
                seqManageDAO.delFormScope(map);
            }

            for (SeqScopeDTO insertForm : formScopeList) {
                String category = insertForm.getCategory();
                if(!category.equals("F")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("seqCode", seqCode);
                map.put("formCode", insertForm.getUseId());
                seqManageDAO.insertIgnoreFormScope(map);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
}
