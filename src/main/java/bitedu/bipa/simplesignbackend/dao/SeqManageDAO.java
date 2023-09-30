package bitedu.bipa.simplesignbackend.dao;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SeqManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeqManageDAO {

    SeqManageMapper seqManageMapper;
    CommonMapper commonMapper;

    public SeqManageDAO(SeqManageMapper seqManageMapper, CommonMapper commonMapper) {
        this.seqManageMapper = seqManageMapper;
        this.commonMapper = commonMapper;
    }

    public List<SeqAndCompDTO> selectSeqAndComp(SeqAndCompDTO seqAndCompDTO) {
        List<SeqAndCompDTO> seqAndCompList = seqManageMapper.getSeqAndCompList(seqAndCompDTO);
        return seqAndCompList;
    }

//    public ArrayList<FormAndCompDTO> selectFormAndComp(FormAndCompDTO formAndCompDTO) {
//        ArrayList<FormAndCompDTO> formAndCompList = (ArrayList) formManageMapper.getFormAndCompList(formAndCompDTO);
//        return formAndCompList;
//    }
}
