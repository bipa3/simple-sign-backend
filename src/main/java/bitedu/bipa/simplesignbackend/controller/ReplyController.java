package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.FileResDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReplyResDTO;
import bitedu.bipa.simplesignbackend.service.ReplyService;
import bitedu.bipa.simplesignbackend.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final S3Service s3Service;

    public ReplyController(ReplyService replyService, S3Service s3Service) {
        this.replyService = replyService;
        this.s3Service = s3Service;
    }


    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/{num}")
    public ResponseEntity<List<ReplyResDTO>> showReplyList(@PathVariable("num") int approvalDocId) {
        List<ReplyResDTO> replyList =  replyService.showReplyList(approvalDocId);
        return ResponseEntity.ok(replyList);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/insertLowerReply")
    public ResponseEntity<String> insertLowerReply(@RequestPart ReplyReqDTO replyReqDTO,
                                                   @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        int replyId = replyService.registerReply(replyReqDTO);
        if(files !=null){
            for(MultipartFile file: files) {
                String fileName = file.getOriginalFilename();
                String uniqueFileName = s3Service.makeUniqueFileName(file, "reply");
                String s3Url = s3Service.upload(file, uniqueFileName);
                replyService.insertReplyAttachment(s3Url,fileName, replyId, uniqueFileName);
            }
        }
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

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/fileNames/{num}")
    public ResponseEntity<List<FileResDTO>> getFileNames(@PathVariable("num") int replyId) {
        List<FileResDTO> list = replyService.getFileNames(replyId);
        return ResponseEntity.ok(list);
    }

}
