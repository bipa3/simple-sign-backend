package bitedu.bipa.simplesignbackend.service;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.validation.CommonErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
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

    public ArrayList<DocumentListDTO> selectDocuments(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, String sortStatus, String radioSortValue, String lastApprovalDate, Integer lastDocId) {

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDocsList(viewItems,orgUserId,deptId,estId,compId,itemsPerPage,sortStatus,radioSortValue,lastApprovalDate,lastDocId);
        return docList;
    }

    public int selectDocumentsCount(List<String> viewItems, int orgUserId, int deptId,int estId, int compId,String radioSortValue) {
        int count= approvalBoxDAO.selectDocsCount(viewItems,orgUserId,deptId,estId,compId,radioSortValue);
        return count;
    }

    public ArrayList<DocumentListDTO> selectSearchDocuments(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, String searchInput, String sortStatus, String radioSortValue,String lastApprovalDate,Integer lastDocId) {
        ArrayList<Integer> inputOrgUserId = new ArrayList();
        ArrayList<Integer> inputFormCode = new ArrayList();

        //searchInput이 숫자로만 이루어져 있을 때
        if(searchInput.matches("\\d+")){
            inputOrgUserId.add(Integer.valueOf(searchInput));
            inputFormCode.add(Integer.valueOf(searchInput));
        }else{  //아닐 때
            inputOrgUserId = approvalBoxDAO.selectOrgUserId(searchInput);
            inputFormCode = approvalBoxDAO.selectFormCode(searchInput);
        }

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectSearchDocsList(viewItems,orgUserId,deptId, estId, compId, itemsPerPage,searchInput,sortStatus,radioSortValue,lastApprovalDate,lastDocId,
                inputOrgUserId,inputFormCode);

        return docList;
    }

    public int selectSearchDocumentsCount(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, String searchInput,String radioSortValue) {
        ArrayList<Integer> inputOrgUserId = new ArrayList();
        ArrayList<Integer> inputFormCode = new ArrayList();

        //searchInput이 숫자로만 이루어져 있을 때
        if(searchInput.matches("\\d+")){
            inputOrgUserId.add(Integer.valueOf(searchInput));
            inputFormCode.add(Integer.valueOf(searchInput));
        }else{  //아닐 때
            inputOrgUserId = approvalBoxDAO.selectOrgUserId(searchInput);
            inputFormCode = approvalBoxDAO.selectFormCode(searchInput);
        }

        int count =approvalBoxDAO.selectSearchDocsCount(viewItems,orgUserId,deptId, estId, compId, searchInput,radioSortValue,inputOrgUserId,inputFormCode);

        return count;
    }


    public ArrayList<DocumentListDTO> searchDocuments( int orgUserId, int deptId, int estId, int compId, SearchRequestDTO criteria) {

        List<String> viewItems = criteria.getViewItems();
        int itemsPerPage = criteria.getItemsPerPage();
        String sortStatus = criteria.getSortStatus();
        String radioSortValue = criteria.getRadioSortValue();
        String lastApprovalDate = criteria.getLastApprovalDate();
        Integer lastDocId = criteria.getLastDocId();


        Integer inputDocId = null;
        ArrayList<Integer> inputFormCode  = approvalBoxDAO.selectFormCode(criteria.getSearchDocForm());
        ArrayList<Integer> inputOrgUserId = new ArrayList<>();
        ArrayList<Integer> inputDeptId = new ArrayList<>();

        if(criteria.getSearchApprovUser() != null || criteria.getSearchApprovUser() != "") {
            inputOrgUserId.addAll(approvalBoxDAO.selectOrgUserId(criteria.getSearchApprovUser()));
        }
        if(criteria.getSearchWriter() != null || criteria.getSearchApprovUser() != ""){
            inputOrgUserId.addAll(approvalBoxDAO.selectOrgUserId(criteria.getSearchWriter()));
        }
        if(criteria.getSearchDept() != null || criteria.getSearchDept() != ""){
            inputDeptId.addAll(approvalBoxDAO.selectDeptId(criteria.getSearchDept()));
        }

        ArrayList<DocumentListDTO> docList = approvalBoxDAO.selectDetailSearchDocsList(viewItems, orgUserId, deptId,estId, compId, itemsPerPage, criteria,sortStatus,radioSortValue,lastApprovalDate,lastDocId,inputFormCode,inputOrgUserId,inputDeptId);


        return docList;
    }

    public int searchDocumentsCount( int orgUserId, int deptId, int estId, int compId, SearchRequestDTO criteria) {
        List<String> viewItems = criteria.getViewItems();
        String radioSortValue = criteria.getRadioSortValue();

        Integer inputDocId = null;
        ArrayList<Integer> inputFormCode  = approvalBoxDAO.selectFormCode(criteria.getSearchDocForm());
        ArrayList<Integer> inputOrgUserId = new ArrayList<>();
        ArrayList<Integer> inputDeptId = new ArrayList<>();

        if(criteria.getSearchApprovUser() != null || criteria.getSearchApprovUser() != "") {
            inputOrgUserId.addAll(approvalBoxDAO.selectOrgUserId(criteria.getSearchApprovUser()));
        }
        if(criteria.getSearchWriter() != null || criteria.getSearchApprovUser() != ""){
            inputOrgUserId.addAll(approvalBoxDAO.selectOrgUserId(criteria.getSearchWriter()));
        }
        if(criteria.getSearchDept() != null || criteria.getSearchDept() != ""){
            inputDeptId.addAll(approvalBoxDAO.selectDeptId(criteria.getSearchDept()));
        }



        int count =approvalBoxDAO.selectDetailSearchDocsCount(viewItems, orgUserId, deptId,estId, compId, criteria,radioSortValue,inputFormCode,inputOrgUserId,inputDeptId);


        return count;
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