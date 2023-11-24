package bitedu.bipa.simplesignbackend.controller;
import bitedu.bipa.simplesignbackend.dao.ApprovalBoxDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.ApprovalBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/approvbox")
public class ApprovalBoxController {
    @Autowired
    ApprovalBoxService approvalBoxService;
    @Autowired
    CommonDAO commonDAO;
    @Autowired
    ApprovalBoxDAO approvalBoxDAO;

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/view")
    public ArrayList<DocumentListDTO> viewDocList(
            @SessionAttribute(name = "orgUserId") int orgUserId,
            @SessionAttribute(name = "compId") int compId,
            @SessionAttribute(name = "deptId") int deptId,
            @RequestParam(name = "viewItems") List<String> viewItems,
            @RequestParam(name = "itemsPerPage") int itemsPerPage,
            @RequestParam(name = "searchInput") String searchInput,
            @RequestParam(name = "sortStatus") String sortStatus,
            @RequestParam(name = "radioSortValue") String radioSortValue,
            @RequestParam(name = "lastApprovalDate", required = false) String lastApprovalDate,
            @RequestParam(name = "lastDocId", required = false) Integer lastDocId
    ) {

        int estId = approvalBoxDAO.selectEstId(orgUserId);
        ArrayList<DocumentListDTO> docList;

        if (!searchInput.equals("")) {

            docList=approvalBoxService.selectSearchDocuments(viewItems, orgUserId, deptId, estId, compId, itemsPerPage,searchInput,sortStatus,radioSortValue,lastApprovalDate,lastDocId);
        }else{

            docList = approvalBoxService.selectDocuments(viewItems, orgUserId, deptId,estId,compId, itemsPerPage, sortStatus,radioSortValue,lastApprovalDate,lastDocId);
        }
        return docList;
    }



    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/count")
    public int getDocListCount(
            @SessionAttribute(name = "userId") int userId,
            @SessionAttribute(name = "orgUserId") int orgUserId,
            @SessionAttribute(name = "compId") int compId,
            @SessionAttribute(name = "deptId") int deptId,
            @RequestParam(name = "viewItems") List<String> viewItems,
            @RequestParam(name = "searchInput") String searchInput,
            @RequestParam(name = "radioSortValue") String radioSortValue
    ) {
        int estId = approvalBoxDAO.selectEstId(orgUserId);

        int count=0;


        if (!searchInput.equals("")) {

            count=approvalBoxService.selectSearchDocumentsCount(viewItems, orgUserId, deptId, estId, compId, searchInput,radioSortValue);
        }else{
            count = approvalBoxService.selectDocumentsCount(viewItems, orgUserId, deptId,estId,compId,radioSortValue);
        }

        return count;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/search")
    public ResponseEntity<ArrayList<DocumentListDTO>> searchDocuments(@SessionAttribute(name = "orgUserId") int orgUserId,
                                                                      @SessionAttribute(name = "compId") int compId,
                                                                      @SessionAttribute(name = "deptId") int deptId,

                                                                      @Valid @RequestBody SearchRequestDTO criteria) {
        int estId = approvalBoxDAO.selectEstId(orgUserId);
        ArrayList<DocumentListDTO> result = approvalBoxService.searchDocuments(orgUserId,deptId,estId,compId,criteria);
        return ResponseEntity.ok(result);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/searchCount")
    public ResponseEntity<Integer> searchDocumentsCount(@SessionAttribute(name = "orgUserId") int orgUserId,
                                                        @SessionAttribute(name = "compId") int compId,
                                                        @SessionAttribute(name = "deptId") int deptId,
                                                        @Valid @RequestBody SearchRequestDTO criteria) {
        int estId = approvalBoxDAO.selectEstId(orgUserId);

        int result = approvalBoxService.searchDocumentsCount( orgUserId,deptId,estId,compId,criteria);
        return ResponseEntity.ok(result);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/list")
    public ArrayList<ApprovalBoxDTO> viewDocBoxList(@NotNull @RequestParam(name="company", required=false)int company){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectApprovalBox(company);
        return boxList;
    }


    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail")
    public ResponseEntity<List<ApprovalBoxDetailDTO>> viewDocBoxDetail(@RequestParam(name="boxId")int boxId){
        ArrayList<ApprovalBoxDetailDTO> detail = approvalBoxService.selectApprovalBoxDetail(boxId);
        if(detail != null && !detail.isEmpty()){
            return new ResponseEntity(detail,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail/viewitem")
    public ArrayList<ViewItemDTO> viewItemList(@RequestParam(name="boxId")int boxId){
        ArrayList<ViewItemDTO> viewItems = approvalBoxService.selectViewItems(boxId);
        return viewItems;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/box/detail/usedept")
    public ArrayList<BoxUseDepartmentDTO> viewBoxUseDept(@RequestParam(name="boxId")int boxId){
        ArrayList<BoxUseDepartmentDTO> UseDept = approvalBoxService.selectBoxUseDept(boxId);

        return UseDept;
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PutMapping("/box/delete")
    public ResponseEntity viewDocBoxDelete(@NotNull @RequestParam(name="boxId") int boxId) {
        Boolean removeBox = approvalBoxService.deleteApprovalBox(boxId);
        if(removeBox){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/boxlist")
    public Map<String, Object> viewApprovalBoxList(@SessionAttribute(name = "orgUserId") int orgUserId, @SessionAttribute(name = "compId") int compId, @SessionAttribute(name = "deptId") int deptId){
        ArrayList<ApprovalBoxDTO> boxList = approvalBoxService.selectCustomBoxList(compId,orgUserId);
        ArrayList<ViewItemDTO> viewItems = approvalBoxDAO.selectCustomBoxViewItems(compId,orgUserId,deptId);
        Map<String, Object> result = new HashMap<>();
        result.put("boxList", boxList);
        result.put("viewItems", viewItems);

        return result;
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PutMapping("/update")
    public ResponseEntity modifyApprovalBox(@Valid @RequestBody ApprovalBoxReqDTO criteria) {

        Boolean updateBox = approvalBoxService.updateApprovalBox(criteria);
        if(updateBox){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/insert")
    public ResponseEntity<Void> createApprovalBox( @RequestBody ApprovalBoxReqDTO criteria) {
        approvalBoxService.createApprovalBox(criteria);
        return ResponseEntity.ok().build();
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/count")
    public int viewDocumentsCount (@SessionAttribute(name = "userId") int userId,
                                   @SessionAttribute(name = "orgUserId") int orgUserId,
                                   @SessionAttribute(name = "compId") int compId,
                                   @SessionAttribute(name = "deptId") int deptId, @NotNull @RequestParam String boxName){
        int estId = approvalBoxDAO.selectEstId(orgUserId);

        int count = approvalBoxService.selectDocumentsCount(orgUserId,deptId,estId,compId,boxName);
        return count;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/doc/read")
    public void checkReadDoc (@SessionAttribute(name = "orgUserId") int orgUserId, @NotNull @RequestParam int docId){
        approvalBoxService.insertReadDoc(orgUserId, docId);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/doc/getread")
    public ArrayList<Integer> getReadDoc (@SessionAttribute(name = "orgUserId") int orgUserId){
        return approvalBoxService.selectReadDoc(orgUserId);
    }

    @Authority(role = {Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/company")
    public ArrayList<CompanyDTO>getUserCompany (@SessionAttribute (name="orgUserId") int orgUserId){
        return approvalBoxService.selectUserCompany(orgUserId);
    }
}


