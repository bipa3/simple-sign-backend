package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.OrgCompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.OrgRespDTO;
import bitedu.bipa.simplesignbackend.service.OrgService;
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
    public ResponseEntity<List<OrgCompanyDTO>> orgTreeView(){
        List<OrgCompanyDTO> orgCompanyDTOList = orgService.orgTreeView();
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
