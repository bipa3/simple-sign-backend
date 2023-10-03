package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import bitedu.bipa.simplesignbackend.model.dto.SearchRequestDTO;
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

}


