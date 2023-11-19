package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.FormManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailScopeDTO;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
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
    public List<FormAndCompDTO> selectFormAndComp(FormAndCompDTO formAndCompDTO) {
        return (ArrayList) formManageMapper.getFormAndCompList(formAndCompDTO);
    }

    public FormDetailResDTO selectFormDetail(int code) {
        return formManageMapper.getFormDetail(code);
    }

    public ArrayList<FormDetailScopeDTO> selectFormScope(int code) {
        return formManageMapper.getFormDetailScope(code);
    }

    public List<FormListDTO> selectFormList(String searchContent) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        BelongOrganizationDTO belong = commonMapper.getBelongs(orgUserId);
        Map<String, Object> map = new HashMap<>();
        map.put("belong", belong);
        map.put("searchContent", searchContent);
        return formManageMapper.selectFormListWithSearch(map);
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

    public void insertFormDetail(FormDetailResDTO formDetail) {
        formManageMapper.createFormDetail(formDetail);
    }

    public void insertScope(Map<String, Object> map) {
        formManageMapper.createFormScope(map);
    }

    public void insertDefaultApprovalLine(Map<String, Object> map) {
        formManageMapper.createDefaultApprovalLine(map);
    }

    public void updateFormDetail(FormDetailResDTO formDetail) {
        formManageMapper.updateFormDetail(formDetail);
    }

    public ArrayList<FormDetailScopeDTO> getFormDetailScope(int formCode) {
        return (ArrayList) formManageMapper.getFormDetailScope(formCode);
    }

    public void delFormScope(Map<String, Object> map) {
        formManageMapper.delFormScope(map);
    }

    public void insertIgnoreFormScope(Map<String, Object> map) {
        formManageMapper.insertIgnoreFormScope(map);
    }

    public void delDefaultLine(Map<String, Object> map) {
        formManageMapper.delDefaultLine(map);
    }

    public void insertIgnoreDefaultLine(Map<String, Object> map) {
        formManageMapper.insertIgnoreDefaultLine(map);
    }

    public void deleteForm(int code) {
        formManageMapper.deleteForm(code);
    }

    public List<FormDTO> searchFormListAll() {
        return formManageMapper.selectFormListAll();
    }

    public List<DefaultApprovalLineDTO> searchDefaultApprovalLineAll(int code) {
        return formManageMapper.selectDefaultApprovalLine(code);
    }

    public List<FormDTO> searchFormByCompId(Integer id) {
        return formManageMapper.getFormByCompId(id);
    }

    public int searchUsedForm(int code) {
        return formManageMapper.getUsedForm(code);
    }
}
