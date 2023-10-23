package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.OrgRespDTO;
import bitedu.bipa.simplesignbackend.service.OrgService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrgController {

    @Autowired
    private OrgService orgService;

    //TreeView
    @GetMapping("/orgTreeView")
    public ResponseEntity<List<OrgCompanyDTO>> orgTreeView(@RequestParam(required = false) int compId){

        int authorityCode = (int) SessionUtils.getAttribute("authorityCode");

        List<OrgCompanyDTO> orgCompanyDTOList = null;
        if(authorityCode == 1){
            orgCompanyDTOList = orgService.orgTreeView();
        } else if (authorityCode == 2 || authorityCode == 3) {
            orgCompanyDTOList = orgService.orgTreeViewComp(compId);    
        }
        
        return ResponseEntity.ok(orgCompanyDTOList);
    }

    // GridView
    @GetMapping("/topGridView/{nodeId}")
    public ResponseEntity<List<OrgRespDTO>> gridGet(@PathVariable String nodeId, @RequestParam String type, @RequestParam boolean isChecked){
        return ResponseEntity.ok(orgService.getGrid(nodeId, type, isChecked));
    }

    // Search
    @GetMapping("/orgsearch")
    public ResponseEntity<List<OrgRespDTO>> orgSearch(@RequestParam String category, @RequestParam String search){
        return ResponseEntity.ok(orgService.searchOrg(category, search));
    }
}
