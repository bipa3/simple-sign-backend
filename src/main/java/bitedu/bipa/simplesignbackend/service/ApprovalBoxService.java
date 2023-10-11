package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
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

    public Map<String, Object> selectDocuments(List<String> viewItems, int userId, int deptId,int estId, int compId, int itemsPerPage, int offset) {
        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDocsList(viewItems,userId,deptId,estId,compId,itemsPerPage,offset);
        ArrayList<DocumentListDTO> allDocList= approvalBoxDAO.selectDocsCount(viewItems,userId,deptId,estId,compId);

        int count = allDocList.size();
        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);


        return result;
    }

    public Map<String, Object> selectSearchDocuments(List<String> viewItems, int userId, int deptId, int itemsPerPage, int offset, String searchInput) {
        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectSearchDocsList(viewItems,userId,deptId,itemsPerPage,offset,searchInput);
        ArrayList<DocumentListDTO> allDocList=approvalBoxDAO.selectSearchDocsCount(viewItems,userId,deptId,searchInput);
        int count = allDocList.size();

        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }


    public Map<String, Object> searchDocuments( int userId, int deptId, SearchRequestDTO criteria) {
        List<String> viewItems = criteria.getViewItems();
        int itemsPerPage = criteria.getItemsPerPage();
        int offset=criteria.getOffset();

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDetailSearchDocsList(viewItems,userId,deptId, itemsPerPage,offset, criteria);
        ArrayList<DocumentListDTO> allDocList=approvalBoxDAO.selectDetailSearchDocsCount(viewItems,userId,deptId,criteria);


        int count = allDocList.size();
        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }

    public ArrayList<ApprovalBoxDTO> selectApprovalBox(int company) {
        return approvalBoxDAO.selectBoxList(company);
    }

    public ArrayList<ApprovalBoxDetailDTO> selectApprovalBoxDetail(int boxId) {
        return approvalBoxDAO.selectBoxDetail(boxId);
    }

    public void deleteApprovalBox(int boxId) {
        approvalBoxDAO.deleteApprovalBox(boxId);
    }

    public ArrayList<ViewItemDTO> selectViewItems(int boxId) {
        return approvalBoxDAO.selectViewItems(boxId);
    }

    public ArrayList<ApprovalBoxDTO> selectCustomBoxList(int company, int userId, int deptId) {
        return approvalBoxDAO.selectCustomBoxList(company, userId, deptId);
    }

    public void updateApprovalBox(ApprovalBoxReqDTO criteria) {
        ArrayList<String> viewItems = criteria.getViewItems();
        int approvalBoxId = criteria.getApprovalBoxId();
        int compId = criteria.getCompId();
        String approvalBoxName = criteria.getApprovalBoxName();
        int approvalBoxUsedStatus =-1;
        if(criteria.getApprovalBoxUsedStatus() == "미사용") {
            approvalBoxUsedStatus = 0;
        }else{
            approvalBoxUsedStatus = 1;
        }
        char menuUsingRange = criteria.getMenuUsingRange();
        int sortOrder = criteria.getSortOrder();
        approvalBoxDAO.updateApprovalBox(approvalBoxId, compId, approvalBoxName, viewItems,approvalBoxUsedStatus,menuUsingRange,sortOrder);
    }

    public void createApprovalBox(ApprovalBoxReqDTO criteria) {
        ArrayList<String> viewItems = criteria.getViewItems();
        int compId = criteria.getCompId();
        String approvalBoxName = criteria.getApprovalBoxName();
        int approvalBoxUsedStatus =-1;
        if(criteria.getApprovalBoxUsedStatus() == "미사용") {
            approvalBoxUsedStatus = 0;
        }else{
            approvalBoxUsedStatus = 1;
        }
        char menuUsingRange = criteria.getMenuUsingRange();
        int sortOrder = criteria.getSortOrder();
        approvalBoxDAO.createApprovalBox( compId, approvalBoxName, viewItems,approvalBoxUsedStatus,menuUsingRange,sortOrder);
    }
}