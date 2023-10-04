package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import bitedu.bipa.simplesignbackend.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrgController {

    @Autowired
    private OrgService orgService;

    //회사 - 사업장 - 상위부서 - 하위부서
    @GetMapping("/orgTreeView")
    public ResponseEntity<List<OrgCompanyDTO>> orgTreeView(){
        List<OrgCompanyDTO> orgCompanyDTOList = orgService.orgTreeView();
        return ResponseEntity.ok(orgCompanyDTOList);
    }

}
