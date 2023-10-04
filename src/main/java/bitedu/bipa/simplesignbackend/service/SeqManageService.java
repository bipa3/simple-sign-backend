package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.SeqManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
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

    public SeqDetailDTO searchSeqDetail(int code) {
        return seqManageDAO.selectSeqDetail(code);
    }

    public Boolean removeSeq(int code) {
        return seqManageDAO.deleteSeq(code);
    }

    public Boolean seqDetailRegist(SeqDetailDTO seqDetail) {
        return seqManageDAO.insertSeqDetail(seqDetail);
    }
}
