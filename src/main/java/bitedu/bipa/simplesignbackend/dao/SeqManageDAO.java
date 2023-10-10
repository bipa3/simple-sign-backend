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
        List<SeqAndCompDTO> seqAndCompList = seqManageMapper.getSeqAndCompList(seqAndCompDTO);
        return seqAndCompList;
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

    public String selectSeqItems (List<String> seqItems) {
        Map<String, Object> map = new HashMap<>();
        map.put("seqItems", seqItems);
        return seqManageMapper.selectSeqItems(map);
    }


    public Boolean deleteSeq(int code) {
        seqManageMapper.deleteSeq(code);
        return true;
    }

    @Transactional
    public Boolean insertSeqDetail(SeqDetailDTO seqDetail) {
        seqManageMapper.createSeqDetail(seqDetail);
        int seqCode = commonMapper.getLastInsertId();
        System.out.println("seqCode:" + seqCode);
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

    @Transactional
    public Boolean updateSeqDetail(SeqDetailDTO seqDetailDTO) {
        seqManageMapper.updateSeqDetail(seqDetailDTO);
        int seqCode = Integer.parseInt(seqDetailDTO.getCode());
        List<SeqScopeDTO> deptScopeList = seqDetailDTO.getDeptScope();
        List<SeqScopeDTO> formScopeList = seqDetailDTO.getFormScope();

        List<SeqScopeDTO> defaultDeptScopeList = seqManageMapper.getSeqDeptScope(seqCode);;
        List<SeqScopeDTO> defaultFormScopeList = seqManageMapper.getSeqFormScope(seqCode);

        List<SeqScopeDTO> missingDeptDataList = new ArrayList<>();
        List<SeqScopeDTO> missingFormDataList = new ArrayList<>();

        // 회사 scope
        for (SeqScopeDTO defaultDept : defaultDeptScopeList) {
            if (!deptScopeList.contains(defaultDept)) {
                missingDeptDataList.add(defaultDept);
            }
        }

        for (SeqScopeDTO delDept : missingDeptDataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", delDept.getCategory());
            map.put("seqCode", seqCode);
            map.put("useId", delDept.getUseId());
            seqManageMapper.delDeptScope(map);
        }

        for (SeqScopeDTO insertDept : deptScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", insertDept.getCategory());
            map.put("seqCode", seqCode);
            map.put("useId", insertDept.getUseId());
            seqManageMapper.insertIgnoreDeptScope(map);
        }

        // 양식 scope
        for (SeqScopeDTO defaultForm : defaultFormScopeList) {
            if (!formScopeList.contains(defaultForm)) {
                missingFormDataList.add(defaultForm);
            }
        }

        for (SeqScopeDTO delForm : missingFormDataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("seqCode", seqCode);
            map.put("formCode", delForm.getUseId());
            seqManageMapper.delFormScope(map);
        }

        for (SeqScopeDTO insertForm : formScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("seqCode", seqCode);
            map.put("formCode", insertForm.getUseId());
            seqManageMapper.insertIgnoreFormScope(map);
        }

        return true;
    }
}
