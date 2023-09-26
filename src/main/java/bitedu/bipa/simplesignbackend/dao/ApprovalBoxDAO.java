package bitedu.bipa.simplesignbackend.dao;


import bitedu.bipa.simplesignbackend.mapper.ApprovalBoxMapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApprovalBoxDAO {
    @Autowired
    ApprovalBoxMapper approvalBoxMapper;

    public ArrayList<DocumentListDTO> selectSendDoc(int userId, int deptId,  int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> sendDocList = approvalBoxMapper.getSendDocList(userId,itemsPerPage,offset);
        return sendDocList;
    }

    public ArrayList<DocumentListDTO> selectTemporDocs(int userId, int deptId,  int itemsPerPage, int offset){
        ArrayList<DocumentListDTO> temporDocList = approvalBoxMapper.getTemporDocList(userId,itemsPerPage,offset);
        return temporDocList;
    }

    public ArrayList<DocumentListDTO> selectPendDocs(int userId, int deptId,  int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> pendDocList = approvalBoxMapper.getPendDocList(userId,itemsPerPage,offset);
        return pendDocList;
    }

    public ArrayList<DocumentListDTO> selectConcludedDocs(int userId, int deptId,  int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> concludedDocList = approvalBoxMapper.getConcludeDocList(userId,itemsPerPage,offset);
        return concludedDocList;
    }

    public ArrayList<DocumentListDTO> selectReferenceDocs(int userId, int deptId,  int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> referenceDocList = approvalBoxMapper.getReferenceDocList(userId,itemsPerPage,offset,deptId);
        return referenceDocList;
    }


}
