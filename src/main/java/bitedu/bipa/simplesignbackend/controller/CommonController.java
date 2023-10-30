package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.CommonService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    CommonService commonService;

    public CommonController (CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping("/comp")
    public ResponseEntity<ArrayList<CompanyDTO>> compListSelect() {
        ArrayList<CompanyDTO> companyList = commonService.selectCompList();

        if (companyList != null && !companyList.isEmpty()) {
            return new ResponseEntity<>(companyList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/seq/item")
    public ResponseEntity<List<SeqItemListDTO>> seqItemListSelect() {
        List<SeqItemListDTO> seqItemList = commonService.selectSeqItemList();

        if (seqItemList != null && !seqItemList.isEmpty()) {
            return new ResponseEntity<>(seqItemList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/form/recommend")
    public ResponseEntity<List<FormRecommendResDTO>> recommendedFormSelect() {
        List<FormRecommendResDTO> formRecommendList = commonService.selectRecommendedForm();

        if (formRecommendList != null && !formRecommendList.isEmpty()) {
            return new ResponseEntity<>(formRecommendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/approval/kind")
    public ResponseEntity<List<CommonDTO>> approvalKindListSelect() {
        List<CommonDTO> approvalKindList = commonService.selectApprovalKindList();

        if (approvalKindList != null && !approvalKindList.isEmpty()) {
            return new ResponseEntity<>(approvalKindList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
