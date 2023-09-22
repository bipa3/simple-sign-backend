package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.CommonService;
import bitedu.bipa.simplesignbackend.service.FormManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage/form")
public class FormManageController {
    FormManageService formManageService;

    public FormManageController (FormManageService formManageService) {
        this.formManageService = formManageService;
    }

    @PostMapping("/list")
    public ResponseEntity<ArrayList<FormAndCompDTO>> formAndCompListSearch(@RequestBody FormAndCompDTO formAndCompDTO) {
        ArrayList<FormAndCompDTO> formAndCompList = formManageService.selectFormAndCompList(formAndCompDTO);

        if (formAndCompList != null && !formAndCompList.isEmpty()) {
            return new ResponseEntity<>(formAndCompList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<FormDetailResDTO> formDetailSearch(@PathVariable int code) {
        System.out.println("code:"+code);
        FormDetailResDTO formDetail = formManageService.searchFormDetail(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/formTitleList")
    public ResponseEntity<List<FormListDTO>> formTitleList(){
        int userId = 2;
        List<FormListDTO> dto = formManageService.showFormList(userId);
        System.out.println(dto);
        return ResponseEntity.ok(formManageService.showFormList(userId));
    }

    @GetMapping("/seqTitleList")
    public ResponseEntity<List<SequenceListDTO>> seqTitleList(){
        return ResponseEntity.ok(formManageService.showSeqList());
    }
}
