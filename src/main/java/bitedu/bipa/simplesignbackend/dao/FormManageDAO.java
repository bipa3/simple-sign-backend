package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.FormManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
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

    public List<SequenceListDTO> selectSeqList() {
        return formManageMapper.selectSequence();
    }
}
