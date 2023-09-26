package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalBoxService {
    @Autowired
    ApprovalBoxDAO approvalBoxDAO;

    public Map<String, Object> selectDocuments(List<String> viewItems, int userId, int deptId, int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> docList = new ArrayList<>();
        if (viewItems.contains("send")) {
            docList.addAll(approvalBoxDAO.selectSendDoc(userId, deptId, itemsPerPage, offset));
        }
        if (viewItems.contains("tempor")) {
            docList.addAll(approvalBoxDAO.selectTemporDocs(userId, deptId, itemsPerPage, offset));
        }
        if (viewItems.contains("pend")) {
            docList.addAll(approvalBoxDAO.selectPendDocs(userId, deptId, itemsPerPage, offset));
        }
        if (viewItems.contains("concluded")) {
            docList.addAll(approvalBoxDAO.selectConcludedDocs(userId, deptId, itemsPerPage, offset));
        }
        if (viewItems.contains("reference")) {
            docList.addAll(approvalBoxDAO.selectReferenceDocs(userId, deptId, itemsPerPage, offset));
        }
        int count = docList.size();

        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }

}