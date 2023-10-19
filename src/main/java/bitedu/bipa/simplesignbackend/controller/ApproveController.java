package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalDocDetailDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalDocReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalPermissionResDTO;
import bitedu.bipa.simplesignbackend.service.ApproveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/approve")
public class ApproveController {

    private final ApproveService approveService;

    public ApproveController(ApproveService approveService) {
        this.approveService = approveService;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/register")
    public ResponseEntity<String> approveRegister(@RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        approveService.registerApprovalDoc(approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/approval/{num}")
    public ResponseEntity<String> approveApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.approveApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/return/{num}")
    public ResponseEntity<String> returnApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.returnApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/detail/{num}")
    public ResponseEntity<ApprovalDocDetailDTO> showDetailApprovalDoc(@PathVariable("num") int approvalDocId) {
        ApprovalDocDetailDTO approvalDocDetailDTO =  approveService.showDetailApprovalDoc(approvalDocId);
        return ResponseEntity.ok(approvalDocDetailDTO);

    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PatchMapping("/{num}")
    public ResponseEntity<String>  updateApporvalDoc(@PathVariable("num") int approvalDocId, @RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        approveService.updateApprovalDoc(approvalDocId, approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @DeleteMapping("/{num}")
    public ResponseEntity<String> removeApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.removeApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/permissionList/{num}")
    public ResponseEntity<Boolean> getPermissionList(@PathVariable("num") int approvalDocId){
        boolean hasPermission =  approveService.hasPermission(approvalDocId);
        return ResponseEntity.ok(hasPermission);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasApproval/{num}")
    public ResponseEntity<Boolean> getHasApproval(@PathVariable("num") int approvalDocId){
        boolean hasApproval = approveService.getHasApproval(approvalDocId);
        return ResponseEntity.ok(hasApproval);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasUpdate/{num}")
    public ResponseEntity<Boolean> getHasUpdate(@PathVariable("num") int approvalDocId){
        boolean hasUpdate = approveService.getHasUpdate(approvalDocId);
        return ResponseEntity.ok(hasUpdate);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasDelete/{num}")
    public ResponseEntity<Boolean> getHasDelete(@PathVariable("num") int approvalDocId){
        boolean hasDelete = approveService.getHasDelete(approvalDocId);
        return ResponseEntity.ok(hasDelete);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/cancel/{num}")
    public ResponseEntity<String> cancelApproval(@PathVariable("num") int approvalDocId) {
        approveService.cancelApproval(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PatchMapping("/temp/{num}")
    public ResponseEntity<String>  updateTemporalApprovalDoc(@PathVariable("num") int approvalDocId, @RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        approveService.updateTemporalApprovalDoc(approvalDocId, approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

}
