package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ApprovalBoxService {
    @Autowired
    ApprovalBoxDAO approvalBoxDAO;

    public ArrayList<DocumentListDTO> selectSendDocs(int userId) {
        return approvalBoxDAO.selectSendDoc(userId);
    }

    public ArrayList<DocumentListDTO> selectTemporDocs(int userId) {
        return approvalBoxDAO.selectTemporDocs(userId);
    }

    public ArrayList<DocumentListDTO> selectPendDocs(int userId) {
        return approvalBoxDAO.selectPendDocs(userId);
    }

    public ArrayList<DocumentListDTO> selectConcludedDocs(int userId) {
        return approvalBoxDAO.selectConcludedDocs(userId);
    }

    public ArrayList<DocumentListDTO> selectReferenceDocs(int userId, int deptId) {
        return approvalBoxDAO.selectReferenceDocs(userId,deptId);
    }
}
