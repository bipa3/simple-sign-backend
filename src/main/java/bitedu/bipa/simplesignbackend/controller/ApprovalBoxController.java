package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import bitedu.bipa.simplesignbackend.service.ApprovalBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @RequestParam(name = "offset") int offset
    ) {
        int userId = Integer.parseInt(userIdStr);
        int deptId = commonDAO.selectDeptId(userId);
        System.out.println(deptId);
        return approvalBoxService.selectDocuments(viewItems, userId, deptId, itemsPerPage, offset);
    }
}

