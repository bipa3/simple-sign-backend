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
        int count= approvalBoxDAO.selectDocsCount(viewItems,userId,deptId,estId,compId);


        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);


        return result;
    }

    public Map<String, Object> selectSearchDocuments(List<String> viewItems, int userId, int deptId, int estId, int compId, int itemsPerPage, int offset, String searchInput) {
        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectSearchDocsList(viewItems,userId,deptId, estId, compId, itemsPerPage,offset,searchInput);
        ArrayList<DocumentListDTO> allDocList=approvalBoxDAO.selectSearchDocsCount(viewItems,userId,deptId, estId, compId, searchInput);
        int count = allDocList.size();

        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }


    public Map<String, Object> searchDocuments( int userId, int deptId, int estId, int compId, SearchRequestDTO criteria) {
        List<String> viewItems = criteria.getViewItems();
        int itemsPerPage = criteria.getItemsPerPage();
        int offset=criteria.getOffset();

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDetailSearchDocsList(viewItems, userId, deptId, estId, compId, itemsPerPage,offset, criteria);
        ArrayList<DocumentListDTO> allDocList=approvalBoxDAO.selectDetailSearchDocsCount(viewItems, userId, deptId, estId, compId, criteria);


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
        if("미사용".equals(criteria.getApprovalBoxUsedStatus())) {
            approvalBoxUsedStatus = 0;
        }else{
            approvalBoxUsedStatus = 1;
        }
        String menuUsingRange = criteria.getMenuUsingRange();
        ArrayList<BoxUseDepartmentDTO> boxUseDept = criteria.getBoxUseDept();
        int sortOrder = criteria.getSortOrder();
        approvalBoxDAO.updateApprovalBox(approvalBoxId, compId, approvalBoxName, viewItems,approvalBoxUsedStatus,menuUsingRange,boxUseDept,sortOrder);
    }

    public void createApprovalBox(ApprovalBoxReqDTO criteria) {
        ArrayList<String> viewItems = criteria.getViewItems();
        int compId = criteria.getCompId();
        String approvalBoxName = criteria.getApprovalBoxName();
        int approvalBoxUsedStatus =-1;
        if("미사용".equals(criteria.getApprovalBoxUsedStatus())) {
            approvalBoxUsedStatus = 0;
        }else{
            approvalBoxUsedStatus = 1;
        }
        String menuUsingRange = criteria.getMenuUsingRange();
        ArrayList<BoxUseDepartmentDTO> boxUseDept = criteria.getBoxUseDept();
        int sortOrder = criteria.getSortOrder();
        approvalBoxDAO.createApprovalBox( compId, approvalBoxName, viewItems,approvalBoxUsedStatus,menuUsingRange,boxUseDept,sortOrder);
    }

    public int selectDocumentsCount(int userId, int deptId, int estId, int compId, String boxName) {
        int count = 0;
        if (boxName.equals("상신문서")){
            count = approvalBoxDAO.getSendCount(userId);
        }else if(boxName.equals("미결문서")) {
            count = approvalBoxDAO.getPendCount(userId);
        }else if (boxName.equals("기결문서")) {
            count = approvalBoxDAO.getConcludedCount(userId);
        }else if (boxName.equals("수신참조문서")){
            count = approvalBoxDAO.getReferenceCount(userId,deptId,estId,compId);
        }
        return count;
    }

    public void insertReadDoc(int userId, int docId) {
        approvalBoxDAO.insertDocView(userId, docId);
    }

    public ArrayList<Integer> selectReadDoc(int userId) {
        return approvalBoxDAO.selectReadDoc(userId);
    }

    public String selectUserCompName(int compId) {
        return approvalBoxDAO.selectUserCompName(compId);
    }

    public ArrayList<CompanyDTO> selectUserCompany(int userId) {
        return approvalBoxDAO.selectUserCompany(userId);
    }

    public ArrayList<BoxUseDepartmentDTO> selectBoxUseDept(int boxId) {
        return approvalBoxDAO.selectBoxUseDept(boxId);
    }
}