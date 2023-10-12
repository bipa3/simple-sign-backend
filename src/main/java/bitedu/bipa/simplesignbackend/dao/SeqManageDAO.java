package bitedu.bipa.simplesignbackend.dao;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SeqManageMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
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
        return seqManageMapper.getSeqAndCompList(seqAndCompDTO);
    }

    public SeqDetailDTO selectSeqDetail(int code) {
        return seqManageMapper.getSeqDetail(code);
    }
    public List<SeqScopeDTO> selectDeptScope(int code) {
        return seqManageMapper.getSeqDeptScope(code);
    }
    public List<SeqScopeDTO> selectFormScope(int code) {
        return seqManageMapper.getSeqFormScope(code);
    }

    public String selectSeqItems (Map<String, Object> map) {
        return seqManageMapper.selectSeqItems(map);
    }


    public void deleteSeq(int code) {
        seqManageMapper.deleteSeq(code);
    }

    public int getSeqDetailId() {
        return commonMapper.getLastInsertId();
    }

    public void insertSeqDetail(SeqDetailDTO seqDetail) {
        seqManageMapper.createSeqDetail(seqDetail);
    }

    public void insertSeqDept(Map<String, Object> map) {
        seqManageMapper.createSeqDeptScope(map);
    }

    public void insertSeqForm(Map<String, Object> map) {
        seqManageMapper.createSeqFormScope(map);
    }

    public void updateSeqDetail(SeqDetailDTO seqDetailDTO) {
        seqManageMapper.updateSeqDetail(seqDetailDTO);
    }

    public List<SeqScopeDTO> getSeqDeptScope(int seqCode) {
        return seqManageMapper.getSeqDeptScope(seqCode);
    }

    public void delDeptScope(Map<String, Object> map) {
        seqManageMapper.delDeptScope(map);
    }

    public void insertIgnoreDeptScope(Map<String, Object> map) {
        seqManageMapper.insertIgnoreDeptScope(map);
    }

    public List<SeqScopeDTO> getSeqFormScope(int seqCode) {
        return seqManageMapper.getSeqFormScope(seqCode);
    }

    public void delFormScope(Map<String, Object> map) {
        seqManageMapper.delFormScope(map);
    }

    public void insertIgnoreFormScope(Map<String, Object> map) {
        seqManageMapper.insertIgnoreFormScope(map);
    }
}
