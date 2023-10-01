package bitedu.bipa.simplesignbackend.dao;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SeqManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.model.vo.FormDetailScopeVO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Boolean deleteSeq(int code) {
        seqManageMapper.deleteSeq(code);
        return true;
    }

    @Transactional
    public Boolean insertSeqDetail(SeqDetailDTO seqDetail) {
        seqManageMapper.createSeqDetail(seqDetail);
        int seqCode = commonMapper.getLastInsertId();

        List<SeqScopeDTO> deptScopeList = seqDetail.getDeptScope();
        List<SeqScopeDTO> formScopeList = seqDetail.getFormScope();
        for(SeqScopeDTO deptScope : deptScopeList){
            Map<String, Object> map = new HashMap<>();
            map.put("category", deptScope.getCategory());
            map.put("seqCode", seqCode);
            map.put("useId", deptScope.getUseId());
            seqManageMapper.createSeqDeptScope(map);
        }

        for(SeqScopeDTO formScope : formScopeList){
            Map<String, Object> map = new HashMap<>();
            map.put("seqCode", seqCode);
            map.put("formCode", formScope.getUseId());
            seqManageMapper.createSeqFormScope(map);
        }
        return true;
    }
}
