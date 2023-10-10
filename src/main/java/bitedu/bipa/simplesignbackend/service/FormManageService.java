package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.FormManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Boolean formDetailRegist(FormDetailResDTO formDetail) {
        Boolean result = false;
        try {
            int formCode = formManageDAO.insertFormDetail(formDetail);
            formManageDAO.insertScope(formDetail, formCode);
            formManageDAO.insertDefaultApprovalLine(formDetail, formCode);
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
            formManageDAO.updateScope(formDetail);
            formManageDAO.updateDefaultApprovalLine(formDetail);
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
