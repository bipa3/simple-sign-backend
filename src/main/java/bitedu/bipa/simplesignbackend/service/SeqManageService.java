package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.SeqManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SeqManageService {

    SeqManageDAO seqManageDAO;

    public SeqManageService (SeqManageDAO seqManageDAO) {
        this.seqManageDAO = seqManageDAO;
    }

    public List<SeqAndCompDTO> searchSeqAndCompList(SeqAndCompDTO seqAndCompDTO) {
        return seqManageDAO.selectSeqAndComp(seqAndCompDTO);
    }

//    public ArrayList<FormAndCompDTO> selectFormAndCompList(FormAndCompDTO formAndCompDTO) {
//        return formManageDAO.selectFormAndComp(formAndCompDTO);
//    }
//
//    public FormDetailResDTO searchFormDetail(int code) {
//        return formManageDAO.selectFormDetail(code);
//    }
//
//    public List<FormListDTO> showFormList(int userId) {
//        return formManageDAO.selectFormList(userId);
//    }
}
