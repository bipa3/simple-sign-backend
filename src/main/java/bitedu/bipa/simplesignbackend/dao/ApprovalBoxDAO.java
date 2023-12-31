package bitedu.bipa.simplesignbackend.dao;


import bitedu.bipa.simplesignbackend.mapper.ApprovalBoxManageMapper;
import bitedu.bipa.simplesignbackend.mapper.ApprovalBoxMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApprovalBoxDAO {
    @Autowired
    ApprovalBoxMapper approvalBoxMapper;
    @Autowired
    ApprovalBoxManageMapper approvalBoxManageMapper;

    public ArrayList<DocumentListDTO> selectDocsList(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, int offset, String sortStatus, String radioSortValue) {
        return approvalBoxMapper.getDocumentsByViewItems(orgUserId,itemsPerPage,offset,deptId, estId, compId, viewItems,sortStatus,radioSortValue);
    }


    public ArrayList<DocumentListDTO> selectSearchDocsList(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, int offset, String searchInput, String sortStatus, String radioSortValue) {
        return approvalBoxMapper.getSearchDocumentsByViewItems(orgUserId, itemsPerPage, offset, deptId, estId, compId, viewItems, searchInput, sortStatus,radioSortValue);
    }

    public int selectSearchDocsCount(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, String searchInput,String radioSortValue) {
        return approvalBoxMapper.getSearchDocCountByViewItems(orgUserId, deptId, estId, compId, viewItems, searchInput,radioSortValue);
    }

    public  int selectDocsCount(List<String> viewItems, int orgUserId, int deptId, int estId, int compId,String radioSortValue) {
        return approvalBoxMapper.getDocCountByViewItems(orgUserId,deptId,estId,compId,viewItems,radioSortValue);
    }

    public ArrayList<DocumentListDTO> selectDetailSearchDocsList(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, int itemsPerPage, int offset, SearchRequestDTO criteria, String sortStatus, String radioSortValue) {
        return approvalBoxMapper.getDetailSearchDocsList(orgUserId, deptId, estId, compId, viewItems, itemsPerPage, offset, criteria,sortStatus,radioSortValue);
    }

    public int selectDetailSearchDocsCount(List<String> viewItems, int orgUserId, int deptId, int estId, int compId, SearchRequestDTO criteria, String radioSortValue) {
        return approvalBoxMapper.getDetailSearchDocsCount(orgUserId, deptId, estId, compId, viewItems, criteria,radioSortValue);
    }

    public ArrayList<ApprovalBoxDTO> selectBoxList(int company) {
        return approvalBoxManageMapper.getApprovalBoxList(company);
    }

    public ArrayList<ApprovalBoxDetailDTO> selectBoxDetail(int boxId) {
        return approvalBoxManageMapper.getApprovalBoxDetail(boxId);
    }

    public void deleteApprovalBox(int boxId) {
        approvalBoxManageMapper.upDateDelApprovalBox(boxId);
    }

    public ArrayList<ViewItemDTO> selectViewItems(int boxId) {
        return approvalBoxManageMapper.getViewItems(boxId);
    }

    public ArrayList<CompanyDTO> selectUserCompany( int orgUserId){
        ArrayList<CompanyDTO> companyList = (ArrayList)approvalBoxManageMapper.getUserCompany(orgUserId);
        return companyList;
    }

    public ArrayList<ApprovalBoxDTO> selectCustomBoxList(int compId, int orgUserId) {
        return approvalBoxManageMapper.getCustomBoxList(compId,orgUserId);
    }

    public ArrayList<ViewItemDTO> selectCustomBoxViewItems(int compId, int orgUserId, int deptId) {
        return approvalBoxManageMapper.getCustomBoxViewItems(compId,orgUserId,deptId);
    }

    public void updateApprovalBox(int approvalBoxId, int compId, String approvalBoxName, ArrayList<String> viewItems, int approvalBoxUsedStatus, String menuUsingRange, ArrayList<BoxUseDepartmentDTO> boxUseDept, int sortOrder) {
        approvalBoxManageMapper.updateApprovalBox(approvalBoxId,compId,approvalBoxName,approvalBoxUsedStatus,menuUsingRange,sortOrder);
        if (viewItems.size()>0){
            approvalBoxManageMapper.deleteBoxViewItem(approvalBoxId);
            for (String item : viewItems) {
                approvalBoxManageMapper.insertBoxViewItem(approvalBoxId,item);
            }
        }
        if (boxUseDept.size()>0){
            approvalBoxManageMapper.deleteBoxUseDept(approvalBoxId);
            for (BoxUseDepartmentDTO dto : boxUseDept) {
                approvalBoxManageMapper.insertBoxUseDept(dto, approvalBoxId);
            }
        }
    }

    public int selectEstId(int orgUserId) {
        return approvalBoxManageMapper.getUserEstId(orgUserId);
    }

    public void createApprovalBox(int compId, String approvalBoxName, ArrayList<String> viewItems, int approvalBoxUsedStatus, String menuUsingRange, ArrayList<BoxUseDepartmentDTO> boxUseDept, int sortOrder) {
        approvalBoxManageMapper.insertApprovalBox(compId,approvalBoxName,approvalBoxUsedStatus,menuUsingRange,sortOrder);
        int approvalBoxId = approvalBoxManageMapper.getLastInsertId();
        if (viewItems.size()>0){
            for (String item : viewItems) {
                approvalBoxManageMapper.insertBoxViewItem(approvalBoxId,item);
            }
        }
        if (boxUseDept != null && boxUseDept.size()>0){
            for (BoxUseDepartmentDTO dto : boxUseDept) {
                approvalBoxManageMapper.insertBoxUseDept(dto, approvalBoxId);
            }
        }
    }

    public int getSendCount(int orgUserId) {
        return approvalBoxMapper.selectSendCount(orgUserId);
    }

    public int getPendCount(int orgUserId) {
        return approvalBoxMapper.selectPendCount(orgUserId);
    }

    public int getConcludedCount(int orgUserId) {
        return approvalBoxMapper.selectConcludedCount(orgUserId);
    }

    public int getReferenceCount(int orgUserId, int deptId, int estId, int compId) {
        return approvalBoxMapper.selectReferenceCount(orgUserId,deptId,estId,compId);
    }

    public void insertDocView(int orgUserId, int docId) {
        approvalBoxMapper.insertDocView(orgUserId,docId);
    }

    public ArrayList<Integer> selectReadDoc(int orgUserId) {
        return approvalBoxMapper.selectDocView(orgUserId);
    }

    public String selectUserCompName(int compId) {
        return approvalBoxManageMapper.selectCompName(compId);
    }

    public ArrayList<BoxUseDepartmentDTO> selectBoxUseDept(int boxId) {
        return (ArrayList) approvalBoxManageMapper.selectBoxUseDept(boxId);
    }
}
