package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.FormDetailResDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqDetailDTO;
import bitedu.bipa.simplesignbackend.service.SeqManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage/seq")
public class SeqManageController {

    SeqManageService seqManageService;

    public SeqManageController (SeqManageService seqManageService) {
        this.seqManageService = seqManageService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<SeqAndCompDTO>> seqAndCompListSearch(@RequestBody SeqAndCompDTO seqAndCompDTO) {
        List<SeqAndCompDTO> formAndCompList = seqManageService.searchSeqAndCompList(seqAndCompDTO);

        if (formAndCompList != null && !formAndCompList.isEmpty()) {
            return new ResponseEntity<>(formAndCompList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<SeqDetailDTO> seqDetailSearch(@PathVariable int code) {
        SeqDetailDTO seqDetail = seqManageService.searchSeqDetail(code);

        if (seqDetail != null) {
            return new ResponseEntity<>(seqDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity seqRemove(@PathVariable int code) {
        Boolean removeResult = seqManageService.removeSeq(code);
        if (removeResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/detail")
    public ResponseEntity registSeqDetail(@RequestBody SeqDetailDTO seqDetail){
        System.out.println(seqDetail.toString());
        Boolean createResult = seqManageService.seqDetailRegist(seqDetail);
        if (createResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/detail")
    public ResponseEntity changeSeqDetail(@RequestBody SeqDetailDTO seqDetailDTO){
        Boolean updateResult = seqManageService.seqDetailChange(seqDetailDTO);
        if (updateResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
