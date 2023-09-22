package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.FormManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.FormAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailResDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormListDTO;
import bitedu.bipa.simplesignbackend.model.dto.SequenceListDTO;
import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FormManageDAO {
    FormManageMapper formManageMapper;

    public FormManageDAO(FormManageMapper formManageMapper) {
        this.formManageMapper = formManageMapper;
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

    public List<FormListDTO> selectFormList() {
        return formManageMapper.selectFormListWithSearch();
    }

//    public List<SequenceListDTO> selectSeqList() {
//        return formManageMapper.selectSequence();
//    }
}
