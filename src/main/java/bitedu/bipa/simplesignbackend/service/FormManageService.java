package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.dao.FormManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormManageService {
    FormManageDAO formManageDAO;

    public FormManageService (FormManageDAO formManageDAO) {
        this.formManageDAO = formManageDAO;
    }

    public ArrayList<FormAndCompDTO> selectFormAndCompList(FormAndCompDTO formAndCompDTO) {
        return formManageDAO.selectFormAndComp(formAndCompDTO);
    }

    public FormDetailResDTO searchFormDetail(int code) {
        return formManageDAO.selectFormDetail(code);
    }

    public List<FormListDTO> showFormList(int userId) {
        return formManageDAO.selectFormList(userId);
    }

    public List<SequenceListDTO> showSeqList(int userId, int formCode) {
        return formManageDAO.selectSeqList(userId, formCode);
    }

    public List<FormItemDTO> searchFormItem() {
        return formManageDAO.selectFormItemList();
    }
}
