package bitedu.bipa.simplesignbackend.dao;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SeqManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Repository;

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

    public SeqDetailDTO selectSeqDetail(int code) {
        SeqDetailDTO seqDetail = seqManageMapper.getSeqDetail(code);
        List<SeqScopeDTO> deptScopeList = seqManageMapper.getSeqDeptScope(code);
        List<SeqScopeDTO> formScopeList = seqManageMapper.getSeqFormScope(code);
        seqDetail.setDeptScope(deptScopeList);
        seqDetail.setFormScope(formScopeList);
        return seqDetail;
    }
}
