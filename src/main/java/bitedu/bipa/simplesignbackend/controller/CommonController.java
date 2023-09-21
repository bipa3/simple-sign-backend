package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.service.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

}
