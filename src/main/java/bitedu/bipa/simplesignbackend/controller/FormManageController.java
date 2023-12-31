package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.FormManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/manage/form")
public class FormManageController {
    FormManageService formManageService;

    public FormManageController (FormManageService formManageService) {
        this.formManageService = formManageService;
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PostMapping("/list")
    public ResponseEntity<List<FormAndCompDTO>> formAndCompListSearch(@Valid @RequestBody FormAndCompDTO formAndCompDTO) {
        List<FormAndCompDTO> formAndCompList = formManageService.selectFormAndCompList(formAndCompDTO);

        if (formAndCompList != null && !formAndCompList.isEmpty()) {
            return new ResponseEntity<>(formAndCompList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @GetMapping("/list/all")
    public ResponseEntity<List<FormDTO>> formListSearch( ) {
        List<FormDTO> formList = formManageService.searchFormList();

        if (formList != null) {
            return new ResponseEntity<>(formList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PostMapping("/list/some")
    public ResponseEntity<List<FormDTO>> formListSearchByIds(@RequestBody IdDTO idDTO) {
        List<FormDTO> formList = formManageService.searchFormListByIds(idDTO);

        if (formList != null) {
            return new ResponseEntity<>(formList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @GetMapping("/admin/detail/{code}")
    public ResponseEntity<FormDetailResDTO> formDetailSearchForAdmin(@PathVariable int code) {
        FormDetailResDTO formDetail = formManageService.searchFormDetailForAdmin(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN, Authority.Role.USER})
    @GetMapping("/detail/{code}")
    public ResponseEntity<FormDetailResDTO> formDetailSearch(@PathVariable int code) {
        FormDetailResDTO formDetail = formManageService.searchFormDetail(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @GetMapping("/detail/dal/{code}")
    public ResponseEntity<List<DefaultApprovalLineDTO>> defaultApprovalLineSearch(@PathVariable int code) {
        List<DefaultApprovalLineDTO> formDetail = formManageService.searchDefaultApprovalLine(code);

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @GetMapping("/item/list")
    public ResponseEntity<List<FormItemDTO>> formIteamSearch() {
        List<FormItemDTO> formDetail = formManageService.searchFormItem();

        if (formDetail != null) {
            return new ResponseEntity<>(formDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN, Authority.Role.USER})
    @GetMapping("/formTitleList")
    public ResponseEntity<List<FormListDTO>> formTitleList(@RequestParam(required = false) String searchContent){
        searchContent =  searchContent==null?"":searchContent;
        List<FormListDTO> dto = formManageService.showFormList(searchContent);
        System.out.println(dto);
        return ResponseEntity.ok(dto);
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN, Authority.Role.USER})
    @GetMapping("/seqTitleList")
    public ResponseEntity<List<SequenceListDTO>> seqTitleList(@RequestParam int formCode){
        return ResponseEntity.ok(formManageService.showSeqList(formCode));
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PostMapping("/detail")
    public ResponseEntity registFormDetail(@Valid @RequestBody FormDetailResDTO formDetail){

        Boolean createResult = formManageService.formDetailRegist(formDetail);
        if (createResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
    @PatchMapping("/detail")
    public ResponseEntity changeFormDetail(@Valid @RequestBody FormDetailResDTO formDetail){

        Boolean updateResult = formManageService.formDetailChange(formDetail);
        if (updateResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.MASTER_ADMIN, Authority.Role.DEPT_ADMIN})
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
