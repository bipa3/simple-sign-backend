package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.List;
import java.util.Map;


@Mapper
public interface ApproveMapper {

    Integer insertApprovalDoc(ApprovalDocReqDTO dto);

    int insertApprovalLine(ApprovalLineDTO dto);

    int selectLastInserted();

    int insertReceivedRef(ReceivedRefDTO dto);

    int insertProductNumber(Map map);

    ApprovalResDTO selectApprovalByApprovalId(Map map);

    List<ApprovalResDTO> selectAllApproval(int approvalDocId);

    int updateCurrentApproval(ApprovalResDTO approvalResDTO);

    ApprovalDocResDTO selectApprovalCount(int approvalDocId);

    int updateApprovalDoc(ApprovalDocResDTO approvalDocResDTO);

    ApprovalResDTO selectUpperApproverId(ApprovalResDTO approvalResDTO);

    int updateReceivedRefState(int approvalDocId);

    int updateUpperApproverId(ApprovalResDTO approvalResDTO);

    int selectRecipientId(int approvalDocId);


    List<Integer> selectLowerApproverId(Map map);

    ApprovalDocDetailDTO selectApprovalDocById(int approvalDocId);

    List<ApprovalLineDetailListDTO> selectApprovalDetailLineByApprovalDocId(int approvalDocId);
    List<ApprovalLineListDTO> selectApprovalLineByApprovalDocId(int approvalDocId);

    List<ApprovalLineDetailListDTO> selectReceivedRefList(int approvalDocId);

    List<Integer> selectRecievedRefUserId(int approvalDocId);

    ApprovalOrderResDTO selectUserCountByApprovalDoc(Map map);

    char selectApprovalDocStatus(int approvalDocId);

    ApprovalDocResDTO selectApprovalDocStatusAndVersion(int approvalDocId);

    List<Character> selectApprovalStatusList(Map map);

    int updateApprovalDocFromRequest(ApprovalDocReqDTO approvalDocReqDTO);

    List<Integer> selectUpdateAlarmRecipient(int approvalDocId);

    int deleteReceivedRef(int approvalDocId);

    int selectUserIdByApprovalDoc(int approvalDocId);

    int deleteApprovalDoc(int approvalDocId);

    List<ApprovalPermissionResDTO> selectApprovalUserIdByApprovalDocId(int approvalDocId);

    int updateApprovalDocToCancel(ApprovalCancelReqDTO approvalCancelReqDTO);

    int updateApprovalToCancel(ApprovalCancelReqDTO approvalCancelReqDTO);

    Integer selectApprovalNextLine(Map<String, Integer> map);

    int updateApprovalNextLine(Map map);


    Integer selectOrgUserIdFromApprovalDoc(int approvalDocId);

    Character selectFirstApprovalStatus(int approvalDocId);

    int deleteApprovalLine(Map map);

    int updateTemporalApprovalDoc(ApprovalDocReqDTO approvalDocReqDTO);

    int insertApprovalAttachment(ApprovalAttachmentDTO approvalAttachmentDTO);

    List<ApprovalLineDetailListDTO> selectDefaultApprovalLine(int formCode);

    int insertFavorites(FavoritesReqDTO favoritesReqDTO);

    int deleteFavorites(FavoritesReqDTO favoritesReqDTO);

    List<FavoritesResDTO> selectFavorites(int orgUserId);

    int selectFavoritesByFormCode(FavoritesReqDTO favoritesReqDTO);

    List<FileResDTO> selectFileNamesAndFilePath(int approvalDocId);

    ApprovalDocStatusDTO selectApproverIdAndDocStatusByApprovalDoc(int approvalDocId);

    int updateApprovalDocToTemporal(int approvalDocId);

    int updateApprovalLineToWait(int approvalDocId);

    List<Integer> selectApproverIdList(int approvalDocId);

    int selectFirstOrgUserIdFromApprovalLine(int approvalDocId);

    int updateApprovalStatusAndReceiveDate(Map map);

    List<AllUnApprovedDocDTO> selectAllUnApprovedDocList(int orgUserId);

    void insertSearchContents(@Param("approvalDocId") int approvalDocId, @Param("parsedText")String parsedText);

    void updateSearchContents(@Param("approvalDocId") int approvalDocId, @Param("parsedText")String parsedText);
}
