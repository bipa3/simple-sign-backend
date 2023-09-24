package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.ApprovalReqDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ApproveMapper {

    int insertApprovalDoc(ApprovalReqDTO dto);




}
