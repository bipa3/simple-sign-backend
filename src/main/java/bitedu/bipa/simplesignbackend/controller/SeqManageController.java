package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailResDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import bitedu.bipa.simplesignbackend.service.SeqManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage/seq")
public class SeqManageController {

    SeqManageService seqManageService;

    public SeqManageController (SeqManageService seqManageService) {
        this.seqManageService = seqManageService;
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PostMapping("/list")
    public ResponseEntity<List<SeqAndCompDTO>> seqAndCompListSearch(@Valid @RequestBody SeqAndCompDTO seqAndCompDTO) {
        List<SeqAndCompDTO> formAndCompList = seqManageService.searchSeqAndCompList(seqAndCompDTO);

        if (formAndCompList != null && !formAndCompList.isEmpty()) {
            return new ResponseEntity<>(formAndCompList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @GetMapping("/detail/{code}")
    public ResponseEntity<SeqDetailDTO> seqDetailSearch(@PathVariable int code) {
        SeqDetailDTO seqDetail = seqManageService.searchSeqDetail(code);

        if (seqDetail != null) {
            return new ResponseEntity<>(seqDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @DeleteMapping("/{code}")
    public ResponseEntity seqRemove(@PathVariable int code) {
        Boolean removeResult = seqManageService.removeSeq(code);
        if (removeResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PostMapping("/detail")
    public ResponseEntity registSeqDetail(@Valid @RequestBody SeqDetailDTO seqDetail){

        Boolean createResult = seqManageService.seqDetailRegist(seqDetail);
        if (createResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PatchMapping("/detail")
    public ResponseEntity changeSeqDetail(@Valid @RequestBody SeqDetailDTO seqDetailDTO){
        Boolean updateResult = seqManageService.seqDetailChange(seqDetailDTO);
        if (updateResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
