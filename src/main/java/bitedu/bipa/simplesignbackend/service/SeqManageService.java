package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.SeqManageDAO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqScopeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        SeqDetailDTO seqDetail = new SeqDetailDTO();
        try {
            seqDetail = seqManageDAO.selectSeqDetail(code);
            List<SeqScopeDTO> deptScopeList = seqManageDAO.selectDeptScope(code);
            List<SeqScopeDTO> formScopeList = seqManageDAO.selectFormScope(code);
            seqDetail.setDeptScope(deptScopeList);
            seqDetail.setFormScope(formScopeList);

            List<String> seqItems = List.of(seqDetail.getSeqString().split(","));
            System.out.println("SeqItems: " + seqItems);
            Map<String, Object> map = new HashMap<>();
            map.put("seqItems", seqItems);
            String seqList = seqManageDAO.selectSeqItems(map);
            System.out.println("seqList: " + seqList);
            seqDetail.setSeqList(seqList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return seqDetail;
    }

    public Boolean removeSeq(int code) {
        Boolean result = false;
        try{
            seqManageDAO.deleteSeq(code);
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public Boolean seqDetailRegist(SeqDetailDTO seqDetail) {
        Boolean result = false;
        try {
            int seqCode = seqManageDAO.getSeqDetailId();

            seqManageDAO.insertSeqDetail(seqDetail);

            List<SeqScopeDTO> deptScopeList = seqDetail.getDeptScope();
            for(SeqScopeDTO deptScope : deptScopeList) {
                Map<String, Object> map = new HashMap<>();
                map.put("category", deptScope.getCategory());
                map.put("seqCode", seqCode);
                map.put("useId", deptScope.getUseId());
                seqManageDAO.insertSeqDept(map);
            }

            List<SeqScopeDTO> formScopeList = seqDetail.getFormScope();
            for(SeqScopeDTO formScope : formScopeList){
                Map<String, Object> map = new HashMap<>();
                map.put("seqCode", seqCode);
                map.put("formCode", formScope.getUseId());
                seqManageDAO.insertSeqForm(map);
            }
            result = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public Boolean seqDetailChange(SeqDetailDTO seqDetailDTO) {
        Boolean result = false;
        try {
        int seqCode = Integer.parseInt(seqDetailDTO.getCode());
        seqManageDAO.updateSeqDetail(seqDetailDTO);

        // 회사 scope
        List<SeqScopeDTO> deptScopeList = seqDetailDTO.getDeptScope();
        List<SeqScopeDTO> defaultDeptScopeList = seqManageDAO.getSeqDeptScope(seqCode);
        List<SeqScopeDTO> missingDeptDataList = new ArrayList<>();

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
            seqManageDAO.delDeptScope(map);
        }

        for (SeqScopeDTO insertDept : deptScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", insertDept.getCategory());
            map.put("seqCode", seqCode);
            map.put("useId", insertDept.getUseId());
            seqManageDAO.insertIgnoreDeptScope(map);
        }

        // 양식 scope
        List<SeqScopeDTO> formScopeList = seqDetailDTO.getFormScope();
        List<SeqScopeDTO> defaultFormScopeList = seqManageDAO.getSeqFormScope(seqCode);
        List<SeqScopeDTO> missingFormDataList = new ArrayList<>();


        for (SeqScopeDTO defaultForm : defaultFormScopeList) {
            if (!formScopeList.contains(defaultForm)) {
                missingFormDataList.add(defaultForm);
            }
        }

        for (SeqScopeDTO delForm : missingFormDataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("seqCode", seqCode);
            map.put("formCode", delForm.getUseId());
            seqManageDAO.delFormScope(map);
        }

        for (SeqScopeDTO insertForm : formScopeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("seqCode", seqCode);
            map.put("formCode", insertForm.getUseId());
            seqManageDAO.insertIgnoreFormScope(map);
        }

        result = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
