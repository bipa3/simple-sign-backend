package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.List;
import java.util.Map;


@Mapper
public interface ApproveMapper {

    int insertApprovalDoc(ApprovalDocReqDTO dto);

    int insertApprovalLine(ApprovalLineDTO dto);

    int selectLastInserted();

    int insertReceivedRef(ReceivedRefDTO dto);

    int insertProductNumber(Map map);

    ApprovalResDTO selectApprovalByApprovalId(Map map);

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


    int selectOrgUserIdFromApprovalDoc(int approvalDocId);

    char selectFirstApprovalStatus(int approvalDocId);
}
