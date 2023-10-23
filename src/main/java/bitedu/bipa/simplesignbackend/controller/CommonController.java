package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.FormRecommendResDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqItemListDTO;
import bitedu.bipa.simplesignbackend.service.CommonService;
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
    public ResponseEntity<List<FormRecommendResDTO>> recommendedFormSelect(@RequestBody int orgUserId) {
//        int orgUserId = 1;
        List<FormRecommendResDTO> formRecommendList = commonService.selectRecommendedForm(orgUserId);

        if (formRecommendList != null && !formRecommendList.isEmpty()) {
            return new ResponseEntity<>(formRecommendList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
