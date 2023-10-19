package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.ReplyReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyResDTO;
import bitedu.bipa.simplesignbackend.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }


    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/{num}")
    public ResponseEntity<List<ReplyResDTO>> showReplyList(@PathVariable("num") int approvalDocId) {
        List<ReplyResDTO> replyList =  replyService.showReplyList(approvalDocId);
        return ResponseEntity.ok(replyList);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/insertLowerReply")
    public ResponseEntity<String> insertLowerReply(@RequestBody ReplyReqDTO replyReqDTO) {
        replyService.registerReply(replyReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PatchMapping("/{num}")
    public ResponseEntity<String> updateReply(@PathVariable("num") int replyId, @RequestBody ReplyReqDTO replyReqDTO) {
        replyReqDTO.setReplyId(replyId);
        replyService.updateReply(replyReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @DeleteMapping("/{num}")
    public ResponseEntity<String> removeReply(@PathVariable("num") int replyId) {
        replyService.removeReply(replyId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/isEdit/{num}")
    public ResponseEntity<Boolean> isEditable(@PathVariable("num") int replyId) {
        boolean isEditable = replyService.showIsEditable(replyId);
        return ResponseEntity.ok(isEditable);
    }

}
