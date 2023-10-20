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
            @RequestParam(name = "viewItems") List<String> viewItems,
            @RequestParam(name = "itemsPerPage") int itemsPerPage,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "searchInput") String searchInput
    ) {
        int deptId = commonDAO.selectDeptId(userId);
        int estId = approvalBoxDAO.selectEstId(userId);
        int compId = approvalBoxDAO.selectUserCompId(userId);

        Map<String, Object> result = new HashMap<>();

        if (!searchInput.equals("")) {
            result=approvalBoxService.selectSearchDocuments(viewItems, userId, deptId, estId, compId, itemsPerPage, offset, searchInput);
        }else{
            result = approvalBoxService.selectDocuments(viewItems, userId, deptId,estId,compId, itemsPerPage, offset);
        }

        return result;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDocuments(@SessionAttribute(name = "userId") int userId, @RequestBody SearchRequestDTO criteria) {
        int deptId = commonDAO.selectDeptId(userId);
        int estId = approvalBoxDAO.selectEstId(userId);
        int compId = approvalBoxDAO.selectUserCompId(userId);

        Map<String, Object> result = approvalBoxService.searchDocuments( userId,deptId,estId,compId,criteria);
        return ResponseEntity.ok(result);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/list")
    public ArrayList<ApprovalBoxDTO> viewDocBoxList(@RequestParam(name="company", required=false)int company){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectApprovalBox(company);
        System.out.println("박스리스트" + boxList);
        return boxList;
    }


    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
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
    @PutMapping("/box/delete")
    public ResponseEntity<Void> viewDocBoxDelete(@RequestParam(name="boxId") int boxId) {
        approvalBoxService.deleteApprovalBox(boxId);
        return ResponseEntity.ok().build();  // 200 OK
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/boxlist")
    public Map<String, Object> viewApprovalBoxList(@SessionAttribute(name = "userId") int userId){
        int deptId = commonDAO.selectDeptId(userId);
        int company = approvalBoxDAO.selectUserCompId(userId);

        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectCustomBoxList(company,userId,deptId);
        ArrayList<ViewItemDTO> viewItems = approvalBoxDAO.selectCustomBoxViewItems(company,userId,deptId);

        Map<String, Object> result = new HashMap<>();
        result.put("boxList", boxList);
        result.put("viewItems", viewItems);

        return result;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PutMapping("/update")
    public ResponseEntity<Void> modifyApprovalBox(@RequestBody ApprovalBoxReqDTO criteria) {
        approvalBoxService.updateApprovalBox(criteria);
        return ResponseEntity.ok().build();
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/insert")
    public ResponseEntity<Void> createApprovalBox(@RequestBody ApprovalBoxReqDTO criteria) {
        approvalBoxService.createApprovalBox(criteria);
        return ResponseEntity.ok().build();
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/count")
    public int viewDocumentsCount (@SessionAttribute(name = "userId") int userId, @RequestParam String boxName){
        int deptId = commonDAO.selectDeptId(userId);
        int estId = approvalBoxDAO.selectEstId(userId);
        int compId = approvalBoxDAO.selectUserCompId(userId);

        int count = approvalBoxService.selectDocumentsCount(userId,deptId,estId,compId,boxName);
        return count;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/doc/read")
    public void checkReadDoc (@SessionAttribute(name = "userId") int userId, @RequestParam int docId){
        approvalBoxService.insertReadDoc(userId, docId);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/getread")
    public ArrayList<Integer> getReadDoc (@SessionAttribute(name = "userId") int userId){
        return approvalBoxService.selectReadDoc(userId);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/company")
    public ArrayList<CompanyDTO>getUserCompany (@SessionAttribute (name="userId") int userId){
        return approvalBoxService.selectUserCompany(userId);
    }
}


