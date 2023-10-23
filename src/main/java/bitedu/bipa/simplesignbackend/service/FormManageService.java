package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.FormManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FormManageService {
    FormManageDAO formManageDAO;

    public FormManageService (FormManageDAO formManageDAO) {
        this.formManageDAO = formManageDAO;
    }

    public List<FormAndCompDTO> selectFormAndCompList(FormAndCompDTO formAndCompDTO) {
        return formManageDAO.selectFormAndComp(formAndCompDTO);
    }

    public FormDetailResDTO searchFormDetail(int code) {
        FormDetailResDTO formDetail = new FormDetailResDTO();
        try{
            formDetail = formManageDAO.selectFormDetail(code);
            ArrayList<FormDetailScopeDTO> formDetailScopeList = formManageDAO.selectFormScope(code);
            formDetail.setScope(formDetailScopeList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return formDetail;
    }

    public List<FormListDTO> showFormList(String searchContent) {
        return formManageDAO.selectFormList(searchContent);
    }

    public List<SequenceListDTO> showSeqList(int userId, int formCode) {
        return formManageDAO.selectSeqList(userId, formCode);
    }

    public List<FormItemDTO> searchFormItem() {
        return formManageDAO.selectFormItemList();
    }

    @Transactional
    public Boolean formDetailRegist(FormDetailResDTO formDetail) {
        Boolean result = false;
        try {
            int formCode = formManageDAO.insertFormDetail(formDetail);

            ArrayList<FormDetailScopeDTO> scopeList = formDetail.getScope();
            for(FormDetailScopeDTO scope : scopeList){
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", scope.getCategory());
                map.put("formCode", formCode);
                map.put("useId", scope.getUseId());
                formManageDAO.insertScope(map);
            }

            List<DefaultApprovalLineDTO> lineList = formDetail.getApprovalLine();

            for(DefaultApprovalLineDTO line : lineList) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", line.getUserId());
                map.put("lineOrder", line.getLineOrder());
                formManageDAO.insertDefaultApprovalLine(map);
            }
            result = true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public Boolean formDetailChange(FormDetailResDTO formDetail) {
        Boolean result = false;
        try {
            formManageDAO.updateFormDetail(formDetail);

            int formCode = formDetail.getCode();
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
                map.put("category", delScope.getCategory());
                map.put("formCode", formCode);
                map.put("useId", delScope.getUseId());
                formManageDAO.delFormScope(map);
            }

            for (FormDetailScopeDTO insertScope : updateScopeList) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("category", insertScope.getCategory());
                map.put("formCode", formCode);
                map.put("useId", insertScope.getUseId());
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
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", delLine.getUserId());
                formManageDAO.delDefaultLine(map);
            }

            for (DefaultApprovalLineDTO insertLine : updateLineList) {
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("formCode", formCode);
                map.put("userId", insertLine.getUserId());
                map.put("lineOrder", insertLine.getLineOrder());
                formManageDAO.insertIgnoreDefaultLine(map);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Boolean removeForm(int code) {
        return formManageDAO.deleteForm(code);
    }

    public List<FormDTO> searchFormList() {
        return formManageDAO.searchFormListAll();
    }

    public List<DefaultApprovalLineDTO> searchDefaultApprovalLine(int code) {
        return formManageDAO.searchDefaultApprovalLineAll(code);
    }
}
