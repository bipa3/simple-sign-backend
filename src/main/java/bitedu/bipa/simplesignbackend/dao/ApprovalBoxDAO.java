package bitedu.bipa.simplesignbackend.dao;


import bitedu.bipa.simplesignbackend.mapper.ApprovalBoxMapper;
import bitedu.bipa.simplesignbackend.model.dto.DocumentListDTO;
import bitedu.bipa.simplesignbackend.model.dto.SearchRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApprovalBoxDAO {
    @Autowired
    ApprovalBoxMapper approvalBoxMapper;

    public ArrayList<DocumentListDTO> selectDocsList(List<String> viewItems, int userId, int deptId, int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> docList = approvalBoxMapper.getDocumentsByViewItems(userId,itemsPerPage,offset,deptId,viewItems);
        return docList;
    }


    public ArrayList<DocumentListDTO> selectSearchDocsList(List<String> viewItems, int userId, int deptId, int itemsPerPage, int offset, String searchInput) {
        ArrayList<DocumentListDTO> docList = approvalBoxMapper.getSearchDocumentsByViewItems(userId,itemsPerPage,offset,deptId,viewItems,searchInput);
        return docList;
    }

    public ArrayList<DocumentListDTO> selectSearchDocsCount(List<String> viewItems, int userId, int deptId, String searchInput) {
        return approvalBoxMapper.getSearchDocCountByViewItems(userId,deptId,viewItems,searchInput);
    }

    public  ArrayList<DocumentListDTO> selectDocsCount(List<String> viewItems, int userId, int deptId) {
        return approvalBoxMapper.getDocCountByViewItems(userId,deptId,viewItems);
    }

    public ArrayList<DocumentListDTO> selectDetailSearchDocsList(List<String> viewItems, int userId, int deptId, int itemsPerPage, int offset, SearchRequestDTO criteria) {
        ArrayList<DocumentListDTO> doclist = approvalBoxMapper.getDetailSearchDocsList(userId,deptId,viewItems,itemsPerPage,offset,criteria);
        return doclist;
    }

    public ArrayList<DocumentListDTO> selectDetailSearchDocsCount(List<String> viewItems, int userId, int deptId, SearchRequestDTO criteria) {
        return approvalBoxMapper.getDetailSearchDocsCount(userId,deptId,viewItems,criteria);
    }

}
