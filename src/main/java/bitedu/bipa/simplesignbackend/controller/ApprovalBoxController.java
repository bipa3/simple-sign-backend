package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import bitedu.bipa.simplesignbackend.service.ApprovalBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/approvbox")
public class ApprovalBoxController {
    @Autowired
    ApprovalBoxService approvalBoxService;


    @GetMapping("/send")
    public ArrayList<DocumentListDTO> sendDocsSelect(HttpSession session) {
        int userId = 1;

        ArrayList<DocumentListDTO> sendDocList = approvalBoxService.selectSendDocs(userId);
        return sendDocList;
    }

    @GetMapping("/tempor")
    public ArrayList<DocumentListDTO> temporDocsSelect(HttpSession session) {
        int userId = 1;

        ArrayList<DocumentListDTO> temporDocList = approvalBoxService.selectTemporDocs(userId);
        return temporDocList;

    }

    @GetMapping("/pend")
    public ArrayList<DocumentListDTO> pendDocsSelect(HttpSession session) {
        int userId = 1;

        ArrayList<DocumentListDTO> pendDocList = approvalBoxService.selectPendDocs(userId);
        return pendDocList;

    }

    @GetMapping("/concluded")
    public ArrayList<DocumentListDTO> concludedDocsSelect(HttpSession session) {
        int userId = 1;

        ArrayList<DocumentListDTO> concludedDocList = approvalBoxService.selectConcludedDocs(userId);
        return concludedDocList;
    }

    @GetMapping("/reference")
    public ArrayList<DocumentListDTO> referenceDocsSelect(HttpSession session) {
        int deptId = 1;
        int userId = 1;

        ArrayList<DocumentListDTO> referenceDocList = approvalBoxService.selectReferenceDocs(userId,deptId);
        return referenceDocList;
    }

}
