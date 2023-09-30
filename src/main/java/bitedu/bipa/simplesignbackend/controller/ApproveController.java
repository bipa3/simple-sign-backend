package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.ApprovalReqDTO;
import bitedu.bipa.simplesignbackend.service.ApproveService;
import bitedu.bipa.simplesignbackend.service.SequenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approve")
public class ApproveController {

    ApproveService approveService;

    public ApproveController(ApproveService approveService) {
        this.approveService = approveService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> ApproveRegister(@RequestBody ApprovalReqDTO approvalReqDTO) {
        int userId = 1;
        approveService.registerApprovalDoc(approvalReqDTO,userId);

        return ResponseEntity.ok("ok");
    }

}
