package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.ApprovalBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/approvbox")
public class ApprovalBoxController {
    @Autowired
    ApprovalBoxService approvalBoxService;
    @Autowired
    CommonDAO commonDAO;
    @Autowired
    ApprovalBoxDAO approvalBoxDAO;

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/view")
    public Map<String, Object> viewDocList(
            @SessionAttribute(name = "userId") int userId,
            @SessionAttribute(name = "orgUserId") int orgUserId,
            @SessionAttribute(name = "compId") int compId,
            @SessionAttribute(name = "deptId") int deptId,
            @RequestParam(name = "viewItems") List<String> viewItems,
            @RequestParam(name = "itemsPerPage") int itemsPerPage,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "searchInput") String searchInput
    ) {
        int estId = approvalBoxDAO.selectEstId(userId);

        Map<String, Object> result = new HashMap<>();

        if (!searchInput.equals("")) {
            result=approvalBoxService.selectSearchDocuments(viewItems, userId, deptId, estId, compId, itemsPerPage, offset, searchInput);
        }else{
            result = approvalBoxService.selectDocuments(viewItems, orgUserId, deptId,estId,compId, itemsPerPage, offset);
        }

        return result;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDocuments(@SessionAttribute(name = "userId") int userId,
                                                               @SessionAttribute(name = "orgUserId") int orgUserId,
                                                               @SessionAttribute(name = "compId") int compId,
                                                               @SessionAttribute(name = "deptId") int deptId, @RequestBody SearchRequestDTO criteria) {
        int estId = approvalBoxDAO.selectEstId(userId);


        Map<String, Object> result = approvalBoxService.searchDocuments( orgUserId,deptId,estId,compId,criteria);
        return ResponseEntity.ok(result);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/list")
    public ArrayList<ApprovalBoxDTO> viewDocBoxList(@RequestParam(name="company", required=false)int company){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectApprovalBox(company);
        return boxList;
    }


    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail")
    public ArrayList<ApprovalBoxDetailDTO> viewDocBoxDetail(@RequestParam(name="boxId")int boxId){
        ArrayList<ApprovalBoxDetailDTO> detail = approvalBoxService.selectApprovalBoxDetail(boxId);
        return detail;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail/viewitem")
    public ArrayList<ViewItemDTO> viewItemList(@RequestParam(name="boxId")int boxId){
        ArrayList<ViewItemDTO> viewItems = approvalBoxService.selectViewItems(boxId);
        return viewItems;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail/usedept")
    public ArrayList<BoxUseDepartmentDTO> viewBoxUseDept(@RequestParam(name="boxId")int boxId){
        ArrayList<BoxUseDepartmentDTO> UseDept = approvalBoxService.selectBoxUseDept(boxId);
        return UseDept;
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PutMapping("/box/delete")
    public ResponseEntity<Void> viewDocBoxDelete(@RequestParam(name="boxId") int boxId) {
        approvalBoxService.deleteApprovalBox(boxId);
        return ResponseEntity.ok().build();  // 200 OK
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/boxlist")
    public Map<String, Object> viewApprovalBoxList(@SessionAttribute(name = "orgUserId") int orgUserId, @SessionAttribute(name = "compId") int compId, @SessionAttribute(name = "deptId") int deptId){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectCustomBoxList(compId,orgUserId,deptId);
        ArrayList<ViewItemDTO> viewItems = approvalBoxDAO.selectCustomBoxViewItems(compId,orgUserId,deptId);

        Map<String, Object> result = new HashMap<>();
        result.put("boxList", boxList);
        result.put("viewItems", viewItems);

        return result;
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PutMapping("/update")
    public ResponseEntity<Void> modifyApprovalBox(@RequestBody ApprovalBoxReqDTO criteria) {
        approvalBoxService.updateApprovalBox(criteria);
        return ResponseEntity.ok().build();
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/insert")
    public ResponseEntity<Void> createApprovalBox(@RequestBody ApprovalBoxReqDTO criteria) {
        approvalBoxService.createApprovalBox(criteria);
        return ResponseEntity.ok().build();
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/count")
    public int viewDocumentsCount (@SessionAttribute(name = "userId") int userId,
                                   @SessionAttribute(name = "orgUserId") int orgUserId,
                                   @SessionAttribute(name = "compId") int compId,
                                   @SessionAttribute(name = "deptId") int deptId, @RequestParam String boxName){
        int estId = approvalBoxDAO.selectEstId(userId);

        int count = approvalBoxService.selectDocumentsCount(orgUserId,deptId,estId,compId,boxName);
        return count;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/doc/read")
    public void checkReadDoc (@SessionAttribute(name = "orgUserId") int orgUserId, @RequestParam int docId){
        approvalBoxService.insertReadDoc(orgUserId, docId);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/getread")
    public ArrayList<Integer> getReadDoc (@SessionAttribute(name = "orgUserId") int orgUserId){
        return approvalBoxService.selectReadDoc(orgUserId);
    }

//    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
//    @GetMapping("/company")
//    public ArrayList<CompanyDTO>getUserCompany (@SessionAttribute (name="orgUserId") int orgUserId){
//        return approvalBoxService.selectUserCompany(orgUserId);
//    }
}


