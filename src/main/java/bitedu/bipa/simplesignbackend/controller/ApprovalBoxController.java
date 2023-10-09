package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
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

    @GetMapping("/view")
    public Map<String, Object> viewDocList(
            @SessionAttribute(name = "userId") String userIdStr,
            @RequestParam(name = "viewItems") List<String> viewItems,
            @RequestParam(name = "itemsPerPage") int itemsPerPage,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "searchInput") String searchInput
    ) {
        int userId = Integer.parseInt(userIdStr);
        int deptId = commonDAO.selectDeptId(userId);

        Map<String, Object> result = new HashMap<>();

        if (!searchInput.equals("")) {
            result=approvalBoxService.selectSearchDocuments(viewItems, userId, deptId, itemsPerPage, offset, searchInput);
        }else{
            result = approvalBoxService.selectDocuments(viewItems, userId, deptId, itemsPerPage, offset);
        }

        return result;
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDocuments(@SessionAttribute(name = "userId") String userIdStr, @RequestBody SearchRequestDTO criteria) {
        int userId = Integer.parseInt(userIdStr);
        int deptId = commonDAO.selectDeptId(userId);

        Map<String, Object> result = approvalBoxService.searchDocuments( userId,deptId,criteria);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ArrayList<ApprovalBoxDTO> viewDocBoxList(@RequestParam(name="company", required=false)int company){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectApprovalBox(company);
        return boxList;
    }



    @GetMapping("/box/detail")
    public ArrayList<ApprovalBoxDetailDTO> viewDocBoxDetail(@RequestParam(name="boxId")int boxId){
        ArrayList<ApprovalBoxDetailDTO> detail = approvalBoxService.selectApprovalBoxDetail(boxId);
        return detail;
    }

    @GetMapping("/box/detail/viewitem")
    public ArrayList<ViewItemDTO> viewItemList(@RequestParam(name="boxId")int boxId){
        ArrayList<ViewItemDTO> viewItems = approvalBoxService.selectViewItems(boxId);
        return viewItems;
    }

    @PutMapping("/box/delete")
    public ResponseEntity<Void> viewDocBoxDelete(@RequestParam(name="boxId") int boxId) {
        approvalBoxService.deleteApprovalBox(boxId);
        return ResponseEntity.ok().build();  // 200 OK
    }

    @GetMapping("/boxlist")
    public Map<String, Object> viewApprovalBoxList(@SessionAttribute(name = "userId") String userIdStr){
        int userId = Integer.parseInt(userIdStr);
        int deptId = commonDAO.selectDeptId(userId);
        int company = approvalBoxDAO.selectUserCompId(userId);

        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectCustomBoxList(company,userId,deptId);
        ArrayList<ViewItemDTO> viewItems = approvalBoxDAO.selectCustomBoxViewItems(company,userId,deptId);

        Map<String, Object> result = new HashMap<>();
        result.put("boxList", boxList);
        result.put("viewItems", viewItems);

        return result;
    }

}


