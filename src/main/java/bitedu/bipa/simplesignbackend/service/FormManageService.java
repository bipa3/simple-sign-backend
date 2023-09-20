package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.dao.FormManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailResDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
