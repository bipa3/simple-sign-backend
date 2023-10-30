package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.FormManageDAO;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CommonErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FormManageService {
    FormManageDAO formManageDAO;
    CommonMapper commonMapper;
    CommonService commonService;

    public FormManageService (FormManageDAO formManageDAO, CommonMapper commonMapper, CommonService commonService) {
        this.commonMapper = commonMapper;
        this.formManageDAO = formManageDAO;
        this.commonService = commonService;
    }

    public List<FormAndCompDTO> selectFormAndCompList(FormAndCompDTO formAndCompDTO) {
        commonService.checkDeptMasterAthority(Integer.parseInt(formAndCompDTO.getCompId()));
        List<FormAndCompDTO> formAndCompList = formManageDAO.selectFormAndComp(formAndCompDTO);
        if(formAndCompList.size() < 1){
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        return formAndCompList;
    }

    public FormDetailResDTO searchFormDetail(int code) {
        FormDetailResDTO formDetail = new FormDetailResDTO();
        try{
            formDetail = formManageDAO.selectFormDetail(code);
            commonService.checkDeptMasterAthority(formDetail.getCompId());

            ArrayList<FormDetailScopeDTO> formDetailScopeList = formManageDAO.selectFormScope(code);
            formDetail.setScope(formDetailScopeList);

            if(formDetail.getCode() == 0){
                throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
            }
            List<DefaultApprovalLineDTO> defaultLineList = formManageDAO.searchDefaultApprovalLineAll(formDetail.getCode());
            formDetail.setApprovalLine(defaultLineList);

        }catch(Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return formDetail;
    }

    public List<FormListDTO> showFormList(String searchContent) {
        return formManageDAO.selectFormList(searchContent);
    }

    public List<SequenceListDTO> showSeqList(int formCode) {
        int orgUserId = (int)SessionUtils.getAttribute("orgUserId");
        return formManageDAO.selectSeqList(orgUserId, formCode);
    }

    public List<FormItemDTO> searchFormItem() {
        List<FormItemDTO> formItemDTOList = new ArrayList<>();;
        try {
            formItemDTOList = formManageDAO.selectFormItemList();
            if (formItemDTOList.size() < 1) {
                throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return formItemDTOList;
    }

    @Transactional
    public Boolean formDetailRegist(FormDetailResDTO formDetail) {
        try {
            commonService.checkDeptMasterAthority(formDetail.getCompId());

            formManageDAO.insertFormDetail(formDetail);

            int formCode = commonMapper.getLastInsertId();
            if(formCode == 0){
                throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
            }

            ArrayList<FormDetailScopeDTO> scopeList = formDetail.getScope();
            for(FormDetailScopeDTO scope : scopeList){
                String category = scope.getCategory();
                int userId = scope.getUseId();
                if(!category.equals("C") && !category.equals("E") && !category.equals("D") && !category.equals("U")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", category);
                map.put("formCode", formCode);
                map.put("useId", userId);
                formManageDAO.insertScope(map);
            }

            List<DefaultApprovalLineDTO> lineList = formDetail.getApprovalLine();

            for(DefaultApprovalLineDTO line : lineList) {
                int userId = line.getUserId();
                int lineOrder = line.getLineOrder();
                if(userId == 0 || lineOrder < 1){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", userId);
                map.put("lineOrder", lineOrder);
                formManageDAO.insertDefaultApprovalLine(map);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Transactional
    public Boolean formDetailChange(FormDetailResDTO formDetail) {
        try {
            commonService.checkDeptMasterAthority(formDetail.getCompId());

            formManageDAO.updateFormDetail(formDetail);
            int formCode = formDetail.getCode();
            if(formCode == 0){
                throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
            }

            ArrayList<FormDetailScopeDTO> updateScopeList = formDetail.getScope();
            ArrayList<FormDetailScopeDTO> defaultScopeList = formManageDAO.getFormDetailScope(formCode);
            ArrayList<FormDetailScopeDTO> missingDataList = new ArrayList<>();

            for (FormDetailScopeDTO defaultScope : defaultScopeList) {
                if (!updateScopeList.contains(defaultScope)) {
                    missingDataList.add(defaultScope);
                }
            }

            for (FormDetailScopeDTO delScope : missingDataList) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                String category = delScope.getCategory();
                int userId = delScope.getUseId();
                if(!category.equals("C") && !category.equals("E") && !category.equals("D") && !category.equals("U")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }

                map.put("category", category);
                map.put("formCode", formCode);
                map.put("useId", userId);
                formManageDAO.delFormScope(map);
            }

            for (FormDetailScopeDTO insertScope : updateScopeList) {
                String category = insertScope.getCategory();
                int userId = insertScope.getUseId();
                if(!category.equals("C") && !category.equals("E") && !category.equals("D") && !category.equals("U")){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", category);
                map.put("formCode", formCode);
                map.put("useId", userId);
                formManageDAO.insertIgnoreFormScope(map);
            }

            List<DefaultApprovalLineDTO> updateLineList = formDetail.getApprovalLine();
            List<DefaultApprovalLineDTO> defaultLineList = formManageDAO.searchDefaultApprovalLineAll(formCode);
            List<DefaultApprovalLineDTO> missingLineList = new ArrayList<>();

            for (DefaultApprovalLineDTO defaultLine : defaultLineList) {
                if (!updateLineList.contains(defaultLine)) {
                    missingLineList.add(defaultLine);
                }
            }

            for (DefaultApprovalLineDTO delLine : missingLineList) {
                int userId = delLine.getUserId();
                if (userId < 1){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", userId);
                formManageDAO.delDefaultLine(map);
            }

            for (DefaultApprovalLineDTO insertLine : updateLineList) {
                int userId = insertLine.getUserId();
                int lineOrder = insertLine.getLineOrder();
                if (userId < 1 || lineOrder < 1){
                    throw new RestApiException(CommonErrorCode.UNEXPECTED_TYPE);
                }
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", userId);
                map.put("lineOrder", lineOrder);
                formManageDAO.insertIgnoreDefaultLine(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    public Boolean removeForm(int code) {
        FormDetailResDTO formDetail = new FormDetailResDTO();
        try {
            formDetail = formManageDAO.selectFormDetail(code);
            commonService.checkDeptMasterAthority(formDetail.getCompId());

            formManageDAO.deleteForm(code);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    public List<FormDTO> searchFormList() {
        List<FormDTO> formList = new ArrayList<FormDTO>();
        try{
            formList = formManageDAO.searchFormListAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        return formList;
    }

    public List<DefaultApprovalLineDTO> searchDefaultApprovalLine(int code) {
        List<DefaultApprovalLineDTO> defaultApprovalLineDTOList = new ArrayList<DefaultApprovalLineDTO>();
        try{
            defaultApprovalLineDTOList = formManageDAO.searchDefaultApprovalLineAll(code);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return defaultApprovalLineDTOList;
    }
}