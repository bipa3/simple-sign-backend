package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.validation.CommonErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalBoxService {
    @Autowired
    ApprovalBoxDAO approvalBoxDAO;

    public Map<String, Object> selectDocuments(List<String> viewItems, int orgUserId, int deptId,int estId, int compId, int itemsPerPage, int offset, String sortStatus) {
        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDocsList(viewItems,orgUserId,deptId,estId,compId,itemsPerPage,offset,sortStatus);
        int count= approvalBoxDAO.selectDocsCount(viewItems,orgUserId,deptId,estId,compId);


        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);


        return result;
    }

    public Map<String, Object> selectSearchDocuments(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, int offset, String searchInput,String sortStatus) {
        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectSearchDocsList(viewItems,orgUserId,deptId, estId, compId, itemsPerPage,offset,searchInput,sortStatus);
       int count =approvalBoxDAO.selectSearchDocsCount(viewItems,orgUserId,deptId, estId, compId, searchInput);

        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }


    public Map<String, Object> searchDocuments( int orgUserId, int deptId, int estId, int compId, SearchRequestDTO criteria) {
        List<String> viewItems = criteria.getViewItems();
        int itemsPerPage = criteria.getItemsPerPage();
        int offset=criteria.getOffset();

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDetailSearchDocsList(viewItems, orgUserId, deptId, estId, compId, itemsPerPage,offset, criteria);
        int count =approvalBoxDAO.selectDetailSearchDocsCount(viewItems, orgUserId, deptId, estId, compId, criteria);

        Map<String, Object> result = new HashMap<>();
        result.put("docList", docList);
        result.put("count", count);

        return result;
    }

    public ArrayList<ApprovalBoxDTO> selectApprovalBox(int company) {
        return approvalBoxDAO.selectBoxList(company);
    }

    public ArrayList<ApprovalBoxDetailDTO> selectApprovalBoxDetail(int boxId) {
        ArrayList<ApprovalBoxDetailDTO> boxDetail = approvalBoxDAO.selectBoxDetail(boxId);
        if(boxDetail.size()<1){
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        return boxDetail;

    }

    public Boolean deleteApprovalBox(int boxId) {
        try{
            approvalBoxDAO.deleteApprovalBox(boxId);
        }catch(Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    public ArrayList<ViewItemDTO> selectViewItems(int boxId) {
        return approvalBoxDAO.selectViewItems(boxId);
    }

    public ArrayList<ApprovalBoxDTO> selectCustomBoxList(int compId, int orgUserId) {
        return approvalBoxDAO.selectCustomBoxList(compId, orgUserId);
    }

    public Boolean updateApprovalBox(ApprovalBoxReqDTO criteria) {
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
       try{
           approvalBoxDAO.updateApprovalBox(approvalBoxId, compId, approvalBoxName, viewItems,approvalBoxUsedStatus,menuUsingRange,boxUseDept,sortOrder);
       }catch(Exception e){
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
       return true;
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

    public int selectDocumentsCount(int orgUserId, int deptId, int estId, int compId, String boxName) {
        int count = 0;
        if (boxName.equals("상신문서")){
            count = approvalBoxDAO.getSendCount(orgUserId);
        }else if(boxName.equals("미결문서")) {
            count = approvalBoxDAO.getPendCount(orgUserId);
        }else if (boxName.equals("기결문서")) {
            count = approvalBoxDAO.getConcludedCount(orgUserId);
        }else if (boxName.equals("수신참조문서")){
            count = approvalBoxDAO.getReferenceCount(orgUserId,deptId,estId,compId);
        }
        return count;
    }

    public void insertReadDoc(int orgUserId, int docId) {
        approvalBoxDAO.insertDocView(orgUserId, docId);
    }

    public ArrayList<Integer> selectReadDoc(int orgUserId) {
        return approvalBoxDAO.selectReadDoc(orgUserId);
    }


    public ArrayList<CompanyDTO> selectUserCompany(int orgUserId) {
        return approvalBoxDAO.selectUserCompany(orgUserId);
    }

    public ArrayList<BoxUseDepartmentDTO> selectBoxUseDept(int boxId) {
        return approvalBoxDAO.selectBoxUseDept(boxId);
    }
}