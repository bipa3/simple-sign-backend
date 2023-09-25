package bitedu.bipa.simplesignbackend.dao;

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

    public int insertApprovalDoc(ApprovalReqDTO approvalReqDTO, int userId) {
        PositionAndGradeDTO positionAndGradeDTO = commonMapper.getPositionAndGrade(userId);
        approvalReqDTO.setPositionName(positionAndGradeDTO.getPositionName());
        approvalReqDTO.setGradeName(positionAndGradeDTO.getGradeName());
        approvalReqDTO.setDeptId(positionAndGradeDTO.getDeptId());
        approvalReqDTO.setUserId(userId);

        int affectedCount =  approveMapper.insertApprovalDoc(approvalReqDTO);
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

    public int selectMaxProductNumber(int seqCode, int formCode) {
        Map<String, Integer> map = new HashMap();
        map.put("seqCode", seqCode);
        map.put("formCode", formCode);

         return approveMapper.selectMaxProductNumber(map);
    }

    public List<ProductNumberDTO> selectSequenceForm(int seqCode) {
        return approveMapper.selectSequenceForm(seqCode);
    }

    public int insertProductNumber(SequenceUseFormDTO sequenceUseFormDTO) {
        return approveMapper.insertProductNumber(sequenceUseFormDTO);
    }
}
