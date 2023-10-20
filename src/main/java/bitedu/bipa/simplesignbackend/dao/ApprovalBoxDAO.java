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

    public ArrayList<DocumentListDTO> selectDocsList(List<String> viewItems, int userId, int deptId, int estId, int compId, int itemsPerPage, int offset) {
        return approvalBoxMapper.getDocumentsByViewItems(userId,itemsPerPage,offset,deptId, estId, compId, viewItems);
    }


    public ArrayList<DocumentListDTO> selectSearchDocsList(List<String> viewItems, int userId, int deptId, int estId, int compId, int itemsPerPage, int offset, String searchInput) {
        return approvalBoxMapper.getSearchDocumentsByViewItems(userId, itemsPerPage, offset, deptId, estId, compId, viewItems, searchInput);
    }

    public ArrayList<DocumentListDTO> selectSearchDocsCount(List<String> viewItems, int userId, int deptId, int estId, int compId, String searchInput) {
        return approvalBoxMapper.getSearchDocCountByViewItems(userId, deptId, estId, compId, viewItems, searchInput);
    }

    public  int selectDocsCount(List<String> viewItems, int userId, int deptId, int estId, int compId) {
        return approvalBoxMapper.getDocCountByViewItems(userId,deptId,estId,compId,viewItems);
    }

    public ArrayList<DocumentListDTO> selectDetailSearchDocsList(List<String> viewItems, int userId, int deptId, int estId, int compId, int itemsPerPage, int offset, SearchRequestDTO criteria) {
        return approvalBoxMapper.getDetailSearchDocsList(userId, deptId, estId, compId, viewItems, itemsPerPage, offset, criteria);
    }

    public ArrayList<DocumentListDTO> selectDetailSearchDocsCount(List<String> viewItems, int userId, int deptId, int estId, int compId, SearchRequestDTO criteria) {
        return approvalBoxMapper.getDetailSearchDocsCount(userId, deptId, estId, compId, viewItems, criteria);
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
    public int selectUserCompId( int userId){
        return approvalBoxManageMapper.getUserCompId(userId);
    }
    public ArrayList<CompanyDTO> selectUserCompany( int userId){
        ArrayList<CompanyDTO> companyList = (ArrayList)approvalBoxManageMapper.getUserCompany(userId);
        System.out.println("Dao : " +companyList);
        return companyList;
    }

    public ArrayList<ApprovalBoxDTO> selectCustomBoxList(int company, int userId, int deptId) {
        return approvalBoxManageMapper.getCustomBoxList(company,userId,deptId);
    }

    public ArrayList<ViewItemDTO> selectCustomBoxViewItems(int company, int userId, int deptId) {
        return approvalBoxManageMapper.getCustomBoxViewItems(company,userId,deptId);
    }

    public void updateApprovalBox(int approvalBoxId, int compId, String approvalBoxName, ArrayList<String> viewItems, int approvalBoxUsedStatus, char menuUsingRange, int sortOrder) {
        approvalBoxManageMapper.updateApprovalBox(approvalBoxId,compId,approvalBoxName,approvalBoxUsedStatus,menuUsingRange,sortOrder);
        if (viewItems.size()>0){
            approvalBoxManageMapper.deleteBoxViewItem(approvalBoxId);
            for (String item : viewItems) {
                approvalBoxManageMapper.insertBoxViewItem(approvalBoxId,item);
            }
        }
    }

    public int selectEstId(int userId) {
        return approvalBoxManageMapper.getUserEstId(userId);
    }

    public void createApprovalBox(int compId, String approvalBoxName, ArrayList<String> viewItems, int approvalBoxUsedStatus, char menuUsingRange, int sortOrder) {
        approvalBoxManageMapper.insertApprovalBox(approvalBoxName,approvalBoxUsedStatus,menuUsingRange,sortOrder);
        int approvalBoxId = approvalBoxManageMapper.getLastInsertId();

        approvalBoxManageMapper.insertBoxUseCompany(approvalBoxId,compId);
        if (viewItems.size()>0){
            for (String item : viewItems) {
                approvalBoxManageMapper.insertBoxViewItem(approvalBoxId,item);
            }
        }
    }

    public int getSendCount(int userId) {
        return approvalBoxMapper.selectSendCount(userId);
    }

    public int getPendCount(int userId) {
        return approvalBoxMapper.selectPendCount(userId);
    }

    public int getConcludedCount(int userId) {
        return approvalBoxMapper.selectConcludedCount(userId);
    }

    public int getReferenceCount(int userId, int deptId, int estId, int compId) {
        return approvalBoxMapper.selectReferenceCount(userId,deptId,estId,compId);
    }

    public void insertDocView(int userId, int docId) {
        approvalBoxMapper.insertDocView(userId,docId);
    }

    public ArrayList<Integer> selectReadDoc(int userId) {
        return approvalBoxMapper.selectDocView(userId);
    }

    public String selectUserCompName(int compId) {
        return approvalBoxManageMapper.selectCompName(compId);
    }
}
