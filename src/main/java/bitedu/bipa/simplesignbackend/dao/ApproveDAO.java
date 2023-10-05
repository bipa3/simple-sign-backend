package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.enums.ApprovalStatus;
import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApproveDAO {

    ApproveMapper approveMapper;
    CommonMapper commonMapper;

    public ApproveDAO(ApproveMapper approveMapper, CommonMapper commonMapper) {
        this.approveMapper = approveMapper;
        this.commonMapper = commonMapper;
    }

    public int insertApprovalDoc(ApprovalDocReqDTO approvalDocReqDTO, int userId) {
        PositionAndGradeDTO positionAndGradeDTO = commonMapper.getPositionAndGrade(userId);
        approvalDocReqDTO.setPositionName(positionAndGradeDTO.getPositionName());
        approvalDocReqDTO.setGradeName(positionAndGradeDTO.getGradeName());
        approvalDocReqDTO.setDeptId(positionAndGradeDTO.getDeptId());
        approvalDocReqDTO.setUserId(userId);

        int affectedCount =  approveMapper.insertApprovalDoc(approvalDocReqDTO);
        if(affectedCount ==0) {
            throw new RuntimeException();
        }
        int lastInsertedId = approveMapper.selectLastInserted();
        return lastInsertedId;
    }

    public int insertApprovalLine(ApprovalLineDTO approvalLineDTO) {
        return approveMapper.insertApprovalLine(approvalLineDTO);
    }

    public int insertReceivedRef(ReceivedRefDTO receivedRefDTO) {
        return approveMapper.insertReceivedRef(receivedRefDTO);
    }

    public int insertProductNumber(String productNum, int approvalDocId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productNum", productNum);
        map.put("approvalDocId", approvalDocId);

        return approveMapper.insertProductNumber(map);
    }

    public ApprovalResDTO selectApprovalByApprovalId(int userId, int approvalDocId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("approvalDocId",approvalDocId);
        return approveMapper.selectApprovalByApprovalId(map);
    }

    public int updateCurrentApproval(ApprovalResDTO approvalResDTO) {
        return approveMapper.updateCurrentApproval(approvalResDTO);
    }

    public ApprovalDocResDTO selectApprovalCount(int approvalDocId) {
        return approveMapper.selectApprovalCount(approvalDocId);
    }

    public int updateApprovalDoc(ApprovalDocResDTO approvalDocResDTO) {
        return approveMapper.updateApprovalDoc(approvalDocResDTO);
    }

    public ApprovalResDTO selectUpperApproverId(ApprovalResDTO approvalResDTO) {
        return approveMapper.selectUpperApproverId(approvalResDTO);
    }

    public int updateReceivedRefState(int approvalDocId) {
        return approveMapper.updateReceivedRefState(approvalDocId);
    }
    public int updateUpperApproverId(ApprovalResDTO approvalResDTO) {
        return approveMapper.updateUpperApproverId(approvalResDTO);
    }

    public int selectRecipientId(int approvalDocId) {
        return approveMapper.selectRecipientId(approvalDocId);
    }

    public List<Integer> selectLowerApproverId(int approvalDocId, int approvalOrder) {
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("approvalOrder", approvalOrder);
        return approveMapper.selectLowerApproverId(map);
    }

    public ApprovalDocDetailDTO selectApprovalDocById(int approvalDocId) {
        return approveMapper.selectApprovalDocById(approvalDocId);
    }

    public List<Integer> selectRecievedRefUserId(int approvalDocId) {
        return approveMapper.selectRecievedRefUserId(approvalDocId);
    }

    public ApprovalOrderResDTO selectUserIdByApprovalDoc(int userId, int approvalDocId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("approvalDocId", approvalDocId);
        return approveMapper.selectUserIdByApprovalDoc(map);

    }

    public boolean isUpdatePossible(int approvalDocId, int approvalOrder) {
        char docStatus = approveMapper.selectApprovalDocStatus(approvalDocId);
        if(docStatus == ApprovalStatus.APPROVAL.getCode() || docStatus == ApprovalStatus.RETURN.getCode()) {
            throw new RuntimeException(); //이미 결재된 문서임
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("approvalOrder", approvalOrder);
        List<Character> approvalStatusList = approveMapper.selectApprovalStatusList(map);
        for(char approvalStatus : approvalStatusList) {
            if(approvalStatus == ApprovalStatus.APPROVAL.getCode() || approvalStatus == ApprovalStatus.RETURN.getCode()) {
                throw  new RuntimeException(); //이미 상위 결재자 중 결재한 사람이 있음
            }
        }
        return true;
    }

    public int updateApprovalDocFromRequest(ApprovalDocReqDTO approvalDocReqDTO) {
        return approveMapper.updateApprovalDocFromRequest(approvalDocReqDTO);
    }

    public List<Integer> selectUpdateAlarmRecipient(int approvalDocId) {
        return approveMapper.selectUpdateAlarmRecipient(approvalDocId);
    }

    public int deleteReceivedRef(int approvalDocId) {
        return approveMapper.deleteReceivedRef(approvalDocId);
    }
}
