package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.FormManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
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
        ArrayList<FormDetailScopeVO> formDetailScopeList = (ArrayList) formManageMapper.getFormDetailScope(code);
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

        ArrayList<FormDetailScopeVO> scopeList = formDetail.getScope();
        for(FormDetailScopeVO scope : scopeList){
            Map<String, Object> map = new HashMap<>();
            map.put("category", scope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", scope.getUseId());
            formManageMapper.createFormScope(map);
        }
        return true;
    }

    @Transactional
    public Boolean updateFormDetail(FormDetailResDTO formDetail) {
        formManageMapper.updateFormDetail(formDetail);

        int formCode = formDetail.getCode();
        ArrayList<FormDetailScopeVO> updateScopeList = formDetail.getScope();
        ArrayList<FormDetailScopeVO> defaultScopeList = (ArrayList) formManageMapper.getFormDetailScope(formCode);
        ArrayList<FormDetailScopeVO> missingDataList = new ArrayList<>();

        for (FormDetailScopeVO defaultScope : defaultScopeList) {
            if (!updateScopeList.contains(defaultScope)) {
                missingDataList.add(defaultScope);
            }
        }

        for (FormDetailScopeVO delScope : missingDataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", delScope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", delScope.getUseId());
            formManageMapper.delFormScope(map);
        }

        for (FormDetailScopeVO insertScope : updateScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", insertScope.getCategory());
            map.put("formCode", formCode);
            map.put("useId", insertScope.getUseId());
            formManageMapper.insertIgnoreFormScope(map);
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
}
