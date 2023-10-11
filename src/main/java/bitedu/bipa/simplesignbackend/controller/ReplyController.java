package bitedu.bipa.simplesignbackend.controller;

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

    @GetMapping("/{num}")
    public ResponseEntity<List<ReplyResDTO>> showReplyList(@PathVariable("num") int approvalDocId) {
        List<ReplyResDTO> replyList =  replyService.showReplyList(approvalDocId);

        return ResponseEntity.ok(replyList);
    }

    @PostMapping("/insertLowerReply")
    public ResponseEntity<String> insertLowerReply(@RequestBody ReplyReqDTO replyReqDTO) {
        int userId= 1;
        System.out.println(replyReqDTO);
        replyService.registerReply(replyReqDTO, userId);
        return ResponseEntity.ok("ok");
    }

}
