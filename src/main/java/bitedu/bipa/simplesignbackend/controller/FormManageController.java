package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.CommonService;
import bitedu.bipa.simplesignbackend.service.FormManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/list/all")
    public ResponseEntity<List<FormDTO>> formListSearch( ) {
        List<FormDTO> formList = formManageService.searchFormList();

        if (formList != null) {
            return new ResponseEntity<>(formList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<FormDetailResDTO> formDetailSearch(@PathVariable int code) {
        FormDetailResDTO formDetail = formManageService.searchFormDetail(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/dal/{code}")
    public ResponseEntity<List<DefaultApprovalLineDTO>> defaultApprovalLineSearch(@PathVariable int code) {
        List<DefaultApprovalLineDTO> formDetail = formManageService.searchDefaultApprovalLine(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/item/list")
    public ResponseEntity<List<FormItemDTO>> formIteamSearch() {
        List<FormItemDTO> formDetail = formManageService.searchFormItem();

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/formTitleList")
    public ResponseEntity<List<FormListDTO>> formTitleList(){
        int userId = 1;
        List<FormListDTO> dto = formManageService.showFormList(userId);
        return ResponseEntity.ok(formManageService.showFormList(userId));
    }

    @GetMapping("/seqTitleList")
    public ResponseEntity<List<SequenceListDTO>> seqTitleList(@RequestParam int formCode){
        int userId = 1;
        return ResponseEntity.ok(formManageService.showSeqList(userId, formCode));
    }

    @PostMapping("/detail")
    public ResponseEntity registFormDetail(@RequestBody FormDetailResDTO formDetail){
        System.out.println(formDetail.toString());
        Boolean createResult = formManageService.formDetailRegist(formDetail);
        if (createResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/detail")
    public ResponseEntity changeFormDetail(@RequestBody FormDetailResDTO formDetail){
        System.out.println(formDetail.toString());
        Boolean updateResult = formManageService.formDetailChange(formDetail);
        if (updateResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity formRemove(@PathVariable int code) {
        Boolean removeResult = formManageService.removeForm(code);
        if (removeResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
