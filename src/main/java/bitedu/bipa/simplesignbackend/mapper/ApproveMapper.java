package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

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

    int updateUpperApproverId(ApprovalResDTO approvalResDTO);

    int selectRecipientId(int approvalDocId);


    List<Integer> selectLowerApproverId(Map map);

    ApprovalDocDetailDTO selectApprovalDocById(int approvalDocId);
}
