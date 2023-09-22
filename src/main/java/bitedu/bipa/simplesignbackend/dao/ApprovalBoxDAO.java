package bitedu.bipa.simplesignbackend.dao;


import bitedu.bipa.simplesignbackend.mapper.ApprovalBoxMapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class ApprovalBoxDAO {
    @Autowired
    ApprovalBoxMapper approvalBoxMapper;

    public ArrayList<DocumentListDTO> selectSendDoc(int userId) {
        ArrayList<DocumentListDTO> sendDocList = approvalBoxMapper.getSendDocList(userId);
        return sendDocList;
    }

    public ArrayList<DocumentListDTO> selectTemporDocs(int userId) {
        ArrayList<DocumentListDTO> temporDocList = approvalBoxMapper.getTemporDocList(userId);
        return temporDocList;
    }

    public ArrayList<DocumentListDTO> selectPendDocs(int userId) {
        ArrayList<DocumentListDTO> pendDocList = approvalBoxMapper.getPendDocList(userId);
        return pendDocList;
    }

    public ArrayList<DocumentListDTO> selectConcludedDocs(int userId) {
        ArrayList<DocumentListDTO> concludedDocList = approvalBoxMapper.getConcludeDocList(userId);
        return concludedDocList;
    }

    public ArrayList<DocumentListDTO> selectReferenceDocs(int userId, int deptId) {
        ArrayList<DocumentListDTO> referenceDocList = approvalBoxMapper.getReferenceDocList(userId,deptId);
        return referenceDocList;
    }
}
