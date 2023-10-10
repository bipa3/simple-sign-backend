package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.SeqManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqScopeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public SeqDetailDTO searchSeqDetail(int code) {
        SeqDetailDTO seqDetail = seqManageDAO.selectSeqDetail(code);
        List<SeqScopeDTO> deptScopeList = seqManageDAO.selectDeptScope(code);
        List<SeqScopeDTO> formScopeList = seqManageDAO.selectFormScope(code);
        seqDetail.setDeptScope(deptScopeList);
        seqDetail.setFormScope(formScopeList);

        List<String> seqItems = List.of(seqDetail.getSeqString().split(","));
        String seqList = seqManageDAO.selectSeqItems(seqItems);
        seqDetail.setSeqList(seqList);

        return seqDetail;
    }

    public Boolean removeSeq(int code) {
        return seqManageDAO.deleteSeq(code);
    }

    public Boolean seqDetailRegist(SeqDetailDTO seqDetail) {
        return seqManageDAO.insertSeqDetail(seqDetail);
    }

    public Boolean seqDetailChange(SeqDetailDTO seqDetailDTO) {
        return seqManageDAO.updateSeqDetail(seqDetailDTO);
    }
}
