package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.SeqAndCompDTO;
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
//
//    @GetMapping("/detail/{code}")
//    public ResponseEntity<FormDetailResDTO> formDetailSearch(@PathVariable int code) {
//        FormDetailResDTO formDetail = formManageService.searchFormDetail(code);
//
//        if (formDetail != null) {
//            return new ResponseEntity<>(formDetail, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
