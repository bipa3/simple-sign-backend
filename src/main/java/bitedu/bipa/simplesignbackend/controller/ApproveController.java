package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.ApprovalDocDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalDocReqDTO;
import bitedu.bipa.simplesignbackend.service.ApproveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approve")
public class ApproveController {

    ApproveService approveService;

    public ApproveController(ApproveService approveService) {
        this.approveService = approveService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> approveRegister(@RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        int userId = 1;
        approveService.registerApprovalDoc(approvalDocReqDTO,userId);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/approval/{num}")
    public ResponseEntity<String> approveApprovalDoc(@PathVariable("num") int approvalDocId) {
        int userId = 2;
        approveService.approveApprovalDoc(userId,approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/return/{num}")
    public ResponseEntity<String> returnApprovalDoc(@PathVariable("num") int approvalDocId) {
        int userId = 1;
        approveService.returnApprovalDoc(userId, approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/detail/{num}")
    public ResponseEntity<ApprovalDocDetailDTO> showDetailApprovalDoc(@PathVariable("num") int approvalDocId) {
        ApprovalDocDetailDTO approvalDocDetailDTO =  approveService.showDetailApprovalDoc(approvalDocId);
        return ResponseEntity.ok(approvalDocDetailDTO);

    }

    @PatchMapping("/{num}")
    public ResponseEntity<String>  updateApporvalDoc(@PathVariable("num") int approvalDocId, @RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        int userId = 1;
        //System.out.println(approvalDocId);
        //System.out.println(approvalDocReqDTO);
        approveService.updateApprovalDoc(userId, approvalDocId, approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> removeApprovalDoc(@PathVariable("num") int approvalDocId) {
        int userId = 1;
        approveService.removeApprovalDoc(userId, approvalDocId);
        return ResponseEntity.ok("ok");
    }

}
