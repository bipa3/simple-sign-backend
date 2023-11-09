package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.enums.ApprovalStatus;
import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        approvalDocReqDTO.setOrgUserId(userId);

        Integer affectedCount =  approveMapper.insertApprovalDoc(approvalDocReqDTO);
        if(affectedCount == null) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        if(affectedCount ==0) {
            throw new RuntimeException();
        }
        //int lastInsertedId = approveMapper.selectLastInserted();
        int lastInsertedId = approvalDocReqDTO.getApprovalDocId();
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

    public ApprovalResDTO selectApprovalByApprovalId(int orgUserId, int approvalDocId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orgUserId", orgUserId);
        map.put("approvalDocId",approvalDocId);
        return approveMapper.selectApprovalByApprovalId(map);
    }

    public List<ApprovalResDTO> selectAllApproval(int approvalDocId) {
        return approveMapper.selectAllApproval(approvalDocId);
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

    public List<ApprovalLineDetailListDTO> selectApprovalDetailLineByApprovalDocId(int approvalDocId) {
        return approveMapper.selectApprovalDetailLineByApprovalDocId(approvalDocId);
    }

    public List<ApprovalLineListDTO> selectApprovalLineByApprovalDocId(int approvalDocId) {
        return approveMapper.selectApprovalLineByApprovalDocId(approvalDocId);
    }

    public List<ApprovalLineDetailListDTO> selectReceivedRefList(int approvalDocId) {
        return approveMapper.selectReceivedRefList(approvalDocId);
    }

    public List<Integer> selectRecievedRefUserId(int approvalDocId) {
        return approveMapper.selectRecievedRefUserId(approvalDocId);
    }

    public ApprovalOrderResDTO selectUserCountByApprovalDoc(int orgUserId, int approvalDocId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orgUserId", orgUserId);
        map.put("approvalDocId", approvalDocId);
        return approveMapper.selectUserCountByApprovalDoc(map);

    }

    public boolean isUpdatePossible(int approvalDocId, int approvalOrder) {
        char docStatus = approveMapper.selectApprovalDocStatus(approvalDocId);
        if(docStatus == ApprovalStatus.APPROVAL.getCode() || docStatus == ApprovalStatus.RETURN.getCode()) {
            throw new RestApiException(CustomErrorCode.APPROVAL_DOC_COMPLETED);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("approvalOrder", approvalOrder);
        List<Character> approvalStatusList = approveMapper.selectApprovalStatusList(map);
        for(char approvalStatus : approvalStatusList) {
            if(approvalStatus == ApprovalStatus.APPROVAL.getCode() || approvalStatus == ApprovalStatus.RETURN.getCode()) {
                throw  new RestApiException(CustomErrorCode.NEXT_APPROVER_APPROVAL_COMPLETE);
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

    public int selectUserIdByApprovalDoc(int approvalDocId) {
        return approveMapper.selectUserIdByApprovalDoc(approvalDocId);
    }

    public int deleteApprovalDoc(int approvalDocId) {
        return approveMapper.deleteApprovalDoc(approvalDocId);
    }

    public List<ApprovalPermissionResDTO> selectApprovalUserIdByApprovalDocId(int approvalDocId) {
        List<ApprovalPermissionResDTO> list = approveMapper.selectApprovalUserIdByApprovalDocId(approvalDocId);
        return list;
    }

    public int updateApprovalDocToCancel(ApprovalCancelReqDTO approvalCancelReqDTO) {
        return approveMapper.updateApprovalDocToCancel(approvalCancelReqDTO);
    }

    public int updateApprovalToCancel(ApprovalCancelReqDTO approvalCancelReqDTO) {
        return approveMapper.updateApprovalToCancel(approvalCancelReqDTO);
    }

    public int updateApprovalNextLine(int approvalDocId, int approvalOrder) {
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("approvalOrder", approvalOrder);
        Integer nextLine = approveMapper.selectApprovalNextLine(map);
        if (nextLine == null) {
            return 1;
        }
        return approveMapper.updateApprovalNextLine(map);
    }

    public Integer selectOrgUserIdFromApprovalDoc(int approvalDocId) {
        return approveMapper.selectOrgUserIdFromApprovalDoc(approvalDocId);
    }

    public Character selectFirstApprovalDocStatus(int approvalDocId) {
        return approveMapper.selectFirstApprovalStatus(approvalDocId);
    }

    public int deleteApprovalLine(int approvalDocId, int isUpdateOrder) {
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("isUpdateOrder",isUpdateOrder);
        return approveMapper.deleteApprovalLine(map);
    }

    public int updateTemporalApprovalDoc(ApprovalDocReqDTO approvalDocReqDTO) {
        return approveMapper.updateTemporalApprovalDoc(approvalDocReqDTO);
    }

    public int insertApprovalAttachment(ApprovalAttachmentDTO approvalAttachmentDTO) {
        return approveMapper.insertApprovalAttachment(approvalAttachmentDTO);
    }


    public List<ApprovalLineDetailListDTO> getDefaultApprovalLine(int formCode) {
        return  approveMapper.selectDefaultApprovalLine(formCode);
    }

    public int insertFavorites(FavoritesReqDTO favoritesReqDTO) {
        return approveMapper.insertFavorites(favoritesReqDTO);
    }

    public int deleteFavorites(FavoritesReqDTO favoritesReqDTO) {
        return approveMapper.deleteFavorites(favoritesReqDTO);
    }

    public List<FavoritesResDTO> getFavorites(int orgUserId) {
        return approveMapper.selectFavorites(orgUserId);
    }

    public boolean selectFavorites(FavoritesReqDTO favoritesReqDTO) {
        int favoritesCount =  approveMapper.selectFavoritesByFormCode(favoritesReqDTO);
        if(favoritesCount ==0) {
            return false;
        }
        return true;
    }

    public List<FileResDTO> selectFileNamesAndFilePath(int approvalDocId) {
        return approveMapper.selectFileNamesAndFilePath(approvalDocId);
    }

    public ApprovalDocStatusDTO selectApproverIdAndDocStatusByApprovalDoc(int approvalDocId) {
        return approveMapper.selectApproverIdAndDocStatusByApprovalDoc(approvalDocId);
    }

    public int updateApprovalDocToTemporal(int approvalDocId) {
        return approveMapper.updateApprovalDocToTemporal(approvalDocId);
    }

    public int updateApprovalLineToWait(int approvalDocId) {
        return approveMapper.updateApprovalLineToWait(approvalDocId);
    }

    public char selectApprovalDocStatus(int approvalDocId) {
        return approveMapper.selectApprovalDocStatus(approvalDocId);
    }

    public List<Integer> selectApproverIdList(int approvalDocId) {
        return approveMapper.selectApproverIdList(approvalDocId);
    }

    public int selectFirstOrgUserIdFromApprovalLine(int approvalDocId) {
        return approveMapper.selectFirstOrgUserIdFromApprovalLine(approvalDocId);
    }

    public int updateApprovalStatusAndReceiveDate(int approvalDocId, int isUpdateOrder) {
        Map<String, Integer> map = new HashMap<>();
        map.put("approvalDocId", approvalDocId);
        map.put("approvalOrder",isUpdateOrder);
        return approveMapper.updateApprovalStatusAndReceiveDate(map);
    }

    public List<Integer> selectAllUnApprovedDocList(int orgUserId) {
        return approveMapper.selectAllUnApprovedDocList(orgUserId);
    }
}
