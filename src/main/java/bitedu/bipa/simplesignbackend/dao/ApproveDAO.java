package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.PositionAndGradeDTO;
import org.springframework.stereotype.Repository;

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

        return approveMapper.insertApprovalDoc(approvalReqDTO);
    }
}
