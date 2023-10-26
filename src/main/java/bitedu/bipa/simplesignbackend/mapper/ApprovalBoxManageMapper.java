package bitedu.bipa.simplesignbackend.mapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;
import java.util.List;


@Mapper
public interface ApprovalBoxManageMapper {
    ArrayList<ApprovalBoxDTO> getApprovalBoxList(@Param("company")int company);

    ArrayList<ApprovalBoxDetailDTO> getApprovalBoxDetail(@Param(("boxId")) int boxId);

    void upDateDelApprovalBox(@Param("boxId")int boxId);

    ArrayList<ViewItemDTO> getViewItems(@Param("boxId")int boxId);

    int getUserCompId(@Param("userId") int userId);

    ArrayList<ApprovalBoxDTO> getCustomBoxList(@Param("company")int company, @Param("userId")int userId, @Param("deptId")int deptId);

    ArrayList<ViewItemDTO> getCustomBoxViewItems(@Param("company")int company, @Param("orgUserId")int userId, @Param("deptId")int deptId);

    void updateApprovalBox(@Param("approvalBoxId")int approvalBoxId, @Param("compId")int compId, @Param("approvalBoxName")String approvalBoxName, @Param("approvalBoxUsedStatus")int approvalBoxUsedStatus, @Param("menuUsingRange")String menuUsingRange, @Param("sortOrder")int sortOrder);

    void deleteBoxViewItem(@Param("approvalBoxId")int approvalBoxId);

    void insertBoxViewItem(@Param("approvalBoxId")int approvalBoxId, @Param("item") String item);

    int getUserEstId(@Param("orgUserId")int orgUserId);

    void insertApprovalBox( @Param("compId") int compId,@Param("approvalBoxName")String approvalBoxName, @Param("approvalBoxUsedStatus")int approvalBoxUsedStatus, @Param("menuUsingRange")String menuUsingRange, @Param("sortOrder")int sortOrder);

    int getLastInsertId();

    String selectCompName(@Param("compId")int compId);

    List<CompanyDTO> getUserCompany(@Param("orgUserId") int orgUserId);

    List<BoxUseDepartmentDTO> selectBoxUseDept(@Param("boxId")int boxId);

    void deleteBoxUseDept(@Param("approvalBoxId")int approvalBoxId);

    void insertBoxUseDept(@Param("dto") BoxUseDepartmentDTO dto, @Param("approvalBoxId") int approvalBoxId);
}