package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalBoxDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalBoxDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.ViewItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;


@Mapper
public interface ApprovalBoxManageMapper {
    ArrayList<ApprovalBoxDTO> getApprovalBoxList(@Param("company")int company);

    ArrayList<ApprovalBoxDetailDTO> getApprovalBoxDetail(@Param(("boxId")) int boxId);

    void upDateDelApprovalBox(@Param("boxId")int boxId);

    ArrayList<ViewItemDTO> getViewItems(@Param("boxId")int boxId);

    int getUserCompId(@Param("userId") int userId);

    ArrayList<ApprovalBoxDTO> getCustomBoxList(@Param("company")int company, @Param("userId")int userId, @Param("deptId")int deptId);

    ArrayList<ViewItemDTO> getCustomBoxViewItems(@Param("company")int company, @Param("userId")int userId, @Param("deptId")int deptId);

    void updateApprovalBox(@Param("approvalBoxId")int approvalBoxId, @Param("compId")int compId, @Param("approvalBoxName")String approvalBoxName, @Param("approvalBoxUsedStatus")int approvalBoxUsedStatus, @Param("menuUsingRange")char menuUsingRange, @Param("sortOrder")int sortOrder);

    void deleteBoxViewItem(@Param("approvalBoxId")int approvalBoxId);

    void insertBoxViewItem(@Param("approvalBoxId")int approvalBoxId, @Param("item") String item);
}