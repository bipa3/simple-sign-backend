package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.FormManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailScopeDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FormManageDAO {
    FormManageMapper formManageMapper;
    CommonMapper commonMapper;

    public FormManageDAO(FormManageMapper formManageMapper, CommonMapper commonMapper) {
        this.formManageMapper = formManageMapper;
        this.commonMapper = commonMapper;
    }
    public ArrayList<FormAndCompDTO> selectFormAndComp(FormAndCompDTO formAndCompDTO) {
        ArrayList<FormAndCompDTO> formAndCompList = (ArrayList) formManageMapper.getFormAndCompList(formAndCompDTO);
        return formAndCompList;
    }

//    @Transactional
    public FormDetailResDTO selectFormDetail(int code) {
        FormDetailResDTO formDetail = formManageMapper.getFormDetail(code);
        ArrayList<FormDetailScopeDTO> formDetailScopeList = (ArrayList) formManageMapper.getFormDetailScope(code);
        formDetail.setScope(formDetailScopeList);
        return formDetail;
    }

    public List<FormListDTO> selectFormList(int userId) {
        BelongOrganizationDTO belong = commonMapper.getBelongs(userId);
        return formManageMapper.selectFormListWithSearch(belong);
    }

    public List<SequenceListDTO> selectSeqList(int userId, int formCode) {
        BelongOrganizationDTO belong = commonMapper.getBelongs(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("belong", belong);
        map.put("formCode", formCode);
        return formManageMapper.selectSequence(map);
    }

    public List<FormItemDTO> selectFormItemList() {
        return formManageMapper.getFormItemList();
    }

    @Transactional
    public Boolean insertFormDetail(FormDetailResDTO formDetail) {
        formManageMapper.createFormDetail(formDetail);
        int formCode = commonMapper.getLastInsertId();

        // 공개범위
        ArrayList<FormDetailScopeDTO> scopeList = formDetail.getScope();
        for(FormDetailScopeDTO scope : scopeList){
            Map<String, Object> map = new HashMap<>();
            map.put("category", scope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", scope.getUseId());
            formManageMapper.createFormScope(map);
        }

        //결재 라인
        List<DefaultApprovalLineDTO> lineList = formDetail.getApprovalLine();

        for(DefaultApprovalLineDTO line : lineList) {
            Map<String, Object> map = new HashMap<>();
            map.put("formCode", formCode);
            map.put("userId", line.getUserId());
            map.put("deptId", line.getDeptId());
            map.put("lineOrder", line.getLineOrder());
            map.put("compId", line.getCompId());
            formManageMapper.createDefaultApprovalLine(map);
        }
        return true;
    }

    @Transactional
    public Boolean updateFormDetail(FormDetailResDTO formDetail) {
        formManageMapper.updateFormDetail(formDetail);

        //공개 범위
        int formCode = formDetail.getCode();
        ArrayList<FormDetailScopeDTO> updateScopeList = formDetail.getScope();
        ArrayList<FormDetailScopeDTO> defaultScopeList = (ArrayList) formManageMapper.getFormDetailScope(formCode);
        ArrayList<FormDetailScopeDTO> missingDataList = new ArrayList<>();

        for (FormDetailScopeDTO defaultScope : defaultScopeList) {
            if (!updateScopeList.contains(defaultScope)) {
                missingDataList.add(defaultScope);
            }
        }

        for (FormDetailScopeDTO delScope : missingDataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", delScope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", delScope.getUseId());
            formManageMapper.delFormScope(map);
        }

        for (FormDetailScopeDTO insertScope : updateScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", insertScope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", insertScope.getUseId());
            formManageMapper.insertIgnoreFormScope(map);
        }

        //결재 라인
        List<DefaultApprovalLineDTO> updateLineList = formDetail.getApprovalLine();
        List<DefaultApprovalLineDTO> defaultLineList = searchDefaultApprovalLineAll(formCode);
        List<DefaultApprovalLineDTO> missingLineList = new ArrayList<>();

        for (DefaultApprovalLineDTO defaultLine : defaultLineList) {
            if (!updateLineList.contains(defaultLine)) {
                missingLineList.add(defaultLine);
            }
        }

        for (DefaultApprovalLineDTO delLine : missingLineList) {
            Map<String, Object> map = new HashMap<>();
            map.put("formCode", formCode);
            map.put("userId", delLine.getUserId());
            map.put("deptId", delLine.getDeptId());
            map.put("compId", delLine.getCompId());
            formManageMapper.delDefaultLine(map);
        }

        for (DefaultApprovalLineDTO insertLine : updateLineList) {
            Map<String, Object> map = new HashMap<>();
            map.put("formCode", formCode);
            map.put("userId", insertLine.getUserId());
            map.put("deptId", insertLine.getDeptId());
            map.put("lineOrder", insertLine.getLineOrder());
            map.put("compId", insertLine.getCompId());
            formManageMapper.insertIgnoreDefaultLine(map);
        }

        return true;
    }

    public Boolean deleteForm(int code) {
        formManageMapper.deleteForm(code);
        return true;
    }

    public List<FormDTO> searchFormListAll() {
        return formManageMapper.selectFormListAll();
    }

    public List<DefaultApprovalLineDTO> searchDefaultApprovalLineAll(int code) {
        return formManageMapper.selectDefaultApprovalLine(code);
    }
}
