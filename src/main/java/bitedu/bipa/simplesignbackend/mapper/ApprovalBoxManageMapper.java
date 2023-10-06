package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalBoxDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalBoxDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;


@Mapper
public interface ApprovalBoxManageMapper {
    ArrayList<ApprovalBoxDTO> getApprovalBoxList(@Param("company")int company);

    ArrayList<ApprovalBoxDetailDTO> getApprovalBoxDetail(@Param(("boxId")) int boxId);

    void upDateDelApprovalBox(@Param("boxId")int boxId);
}