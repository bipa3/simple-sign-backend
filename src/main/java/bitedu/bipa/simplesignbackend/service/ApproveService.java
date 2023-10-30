package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.dao.UserDAO;
import bitedu.bipa.simplesignbackend.enums.AlarmStatus;
import bitedu.bipa.simplesignbackend.enums.ApprovalStatus;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.utils.PasswordUtil;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApproveService {

    private final ApproveDAO approveDAO;
    private final CommonDAO commonDAO;
    private final AlarmService alarmService;
    private final SequenceService sequenceService;
    private final UserDAO userDAO;


    public ApproveService(ApproveDAO approveDAO, CommonDAO commonDAO, AlarmService alarmService, SequenceService sequenceService, UserDAO userDAO) {
        this.approveDAO = approveDAO;
        this.commonDAO = commonDAO;
        this.alarmService = alarmService;
        this.sequenceService = sequenceService;
        this.userDAO = userDAO;
    }

    @Transactional
    public int  registerApprovalDoc(ApprovalDocReqDTO approvalDocReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<Integer> approverList = approvalDocReqDTO.getApproverList();
        int approvalCount = approverList.size();

        approvalDocReqDTO.setApprovalCount(approvalCount);
        approvalDocReqDTO.setCreatedAt(LocalDateTime.now());
        int approvalDocId =approveDAO.insertApprovalDoc(approvalDocReqDTO, orgUserId);


        //docStatus 가 상신인지 임시저장인지 확인
        if(approvalDocReqDTO.getDocStatus() == ApprovalStatus.TEMPORARY.getCode()) {
            //임시저장이면 첨부파일 insert

        }else if(approvalDocReqDTO.getDocStatus() == ApprovalStatus.WAIT.getCode()) {
            //상신이면 alarm, 첨부파일 insert

        }

        //결재라인 삽입
        int count = this.insertApprovalList(approvalDocId,approverList,0);
            if(count-1 !=approvalCount) {
                throw new RestApiException(CustomErrorCode.APPROVALLINE_INSERT_FAIL);
        }

        //수신참조 insert ---> 활성화 여부를 넣고 활성화 된 것만 불러와서 보여주게하기
        List<ReceivedRefDTO> receivedRef = approvalDocReqDTO.getReceiveRefList();
        int totalCount = receivedRef.size();
        int receiveCount = 0;
        for(ReceivedRefDTO dto: receivedRef) {
            dto.setApprovalDocId(approvalDocId);
            receiveCount += approveDAO.insertReceivedRef(dto);
        }
        if(totalCount !=receiveCount) {
            throw new RestApiException(CustomErrorCode.RECEIVED_REF_INSERT_FAIL);
        }
        return approvalDocId;
    }

    @Transactional
    public void approveApprovalDoc(int approvalDocId) {
        //결재하기
        //1. 결재테이블에서 결재할 문서가 있는지 가져오기, 없으면 bad request
        int orgUserId = (int)SessionUtils.getAttribute("orgUserId");
        ApprovalResDTO approvalResDTO =  approveDAO.selectApprovalByApprovalId(orgUserId,approvalDocId);
        if(approvalResDTO.getApprovalId() ==0 || approvalResDTO.getApprovalStatus() !=ApprovalStatus.PROGRESS.getCode()) {
            throw new RestApiException(CustomErrorCode.INAPPROPRIATE_USER);
        }else {
            //2. 결재문서가 있다면 결재 상태를 '승인'으로 바꾸고 결재시간 삽입하기
            approvalResDTO.setApprovalStatus(ApprovalStatus.APPROVAL.getCode());
            approvalResDTO.setApprovalDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateCurrentApproval(approvalResDTO);
            if(affectedCount ==0) {
                throw new RestApiException(CustomErrorCode.APPROVAL_FAIL);
            }
        }

        //3. 결재승인자의 결재순서와 결재문서의 결재 개수가 같은지 확인하고 같다면 결재문서도 '승인'상태로 바꿔주고 종결일자 넣어주고 결재 종결
        ApprovalDocResDTO approvalDocResDTO = approveDAO.selectApprovalCount(approvalDocId);
        boolean isApprovalDocEnd = approvalDocResDTO.getApprovalCount() == approvalResDTO.getApprovalOrder();
        int upperApproverId = 0;
        if(isApprovalDocEnd) {
            approvalDocResDTO.setDocStatus(ApprovalStatus.APPROVAL.getCode());
            approvalDocResDTO.setEndAt(LocalDateTime.now());
            String productNumber = sequenceService.createProductNum(approvalDocResDTO.getSeqCode(), approvalDocResDTO.getOrgUserId());
            approvalDocResDTO.setProductNum(productNumber);

            //수신참조 활성화 업데이트
            approveDAO.updateReceivedRefState(approvalDocId);


        }else {
            //같지 않다면 '진행'으로 바꾸고 상신 상위자의 결재테이블 상태도 '진행'으로, 결재수신시간도 넣어주기
            approvalDocResDTO.setDocStatus(ApprovalStatus.PROGRESS.getCode());
            ApprovalResDTO upperApproverDTO = approveDAO.selectUpperApproverId(approvalResDTO);
            upperApproverId = upperApproverDTO.getOrgUserId();
            upperApproverDTO.setApprovalStatus(ApprovalStatus.PROGRESS.getCode());
            upperApproverDTO.setReceiveDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateUpperApproverId(upperApproverDTO);
            if(affectedCount ==0) {
                throw  new RestApiException(CustomErrorCode.APPROVAL_FAIL);
            }
        }
        int affectedCount = approveDAO.updateApprovalDoc(approvalDocResDTO);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_FAIL);
        }

        //4. 알림보내기(결재승인알람 및 결재문서가 종결이라면 종결알람)
        if(isApprovalDocEnd) {
            //종결일 경우는 상신자와 수신참조자에게 알림
            alarmService.createNewAlarm(approvalDocId, approvalDocResDTO.getOrgUserId(),AlarmStatus.APPROVE.getCode());
            List<Integer> receivedRefUserIdList = approveDAO.selectRecievedRefUserId(approvalDocId);
            if(receivedRefUserIdList.size() !=0) {
                for (int receiveUser : receivedRefUserIdList) {
                    alarmService.createNewAlarm(approvalDocId, receiveUser, AlarmStatus.RECEIVEDREF.getCode());
                }
            }
        }else {
            //미종결일 경우는 상위자에게 알림
            alarmService.createNewAlarm(approvalDocId,upperApproverId,AlarmStatus.SUBMIT.getCode());
        }
    }

    @Transactional
    public void returnApprovalDoc( int approvalDocId) {
        //1. 결재테이블에서 결재할 문서가 있는지 가져오기, 없으면 bad request
        //결재하기
        int orgUserId = (int)SessionUtils.getAttribute("orgUserId");
        ApprovalResDTO approvalResDTO =  approveDAO.selectApprovalByApprovalId(orgUserId,approvalDocId);
        if(approvalResDTO.getApprovalId() ==0 || approvalResDTO.getApprovalStatus() !=ApprovalStatus.PROGRESS.getCode()) {
            throw  new RestApiException(CustomErrorCode.INAPPROPRIATE_USER);
        }else {
            //2. 결재문서가 있다면 결재상태를 '반려'로 바꾸고 결재시간 삽입하기
            approvalResDTO.setApprovalStatus(ApprovalStatus.RETURN.getCode());
            approvalResDTO.setApprovalDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateCurrentApproval(approvalResDTO);
            if(affectedCount ==0) {
                throw new RestApiException(CustomErrorCode.RETURN_FAIL);
            }
        }
        //3. 결재 문서를 '반려' 상태로 바꾸기(이후 결재자 행은 어떻게 할건지?)
        ApprovalDocResDTO approvalDocResDTO = new ApprovalDocResDTO();
        approvalDocResDTO.setDocStatus(ApprovalStatus.RETURN.getCode());
        approvalDocResDTO.setApprovalDocId(approvalDocId);
        approvalDocResDTO.setEndAt(LocalDateTime.now());
        int affectedCount = approveDAO.updateApprovalDoc(approvalDocResDTO);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.RETURN_FAIL);
        }

        //4. 하위 결재자 모두에게 알림 보내기
        int recipientId = approveDAO.selectRecipientId(approvalDocId);
        alarmService.createNewAlarm(approvalDocId,recipientId,AlarmStatus.RETURN.getCode());
        if(approvalResDTO.getApprovalOrder() >1) {
            List<Integer> lowerApproverId = approveDAO.selectLowerApproverId(approvalDocId, approvalResDTO.getApprovalOrder());
            for(int lowerId : lowerApproverId) {
                if(lowerId ==recipientId) continue;
                alarmService.createNewAlarm(approvalDocId, lowerId, AlarmStatus.RETURN.getCode());
            }
        }
    }

    public boolean getHasDetailView(int approvalDocId, ApprovalDocDetailDTO approvalDocDetailDTO) {
        int orgUserId = (int)SessionUtils.getAttribute("orgUserId");

        if(approvalDocDetailDTO ==null) {
            throw new RestApiException(CustomErrorCode.APPROVAL_DOC_DELETED);
        }
        //해당 아이디가 결재라인에도 없고 상신자에도 없고 수신참조문서에 없으면 권한이 없음
        //1.본인이 상신자인지 확인
        Integer approverId = approveDAO.selectOrgUserIdFromApprovalDoc(approvalDocId);
        if(approverId ==null) {
            throw new RestApiException(CustomErrorCode.INAPPROPRIATE_USER);
        }
        boolean sameApprover = false;
        if(approverId == orgUserId) {
            sameApprover = true;
        }
        //2.결재문서에서 결재라인에 해당 사용자가 있는지 확인 + 해당 사용자 전 결재자들이 전부 결재했는지 확인
        List<ApprovalLineListDTO> approvalLineLists = approveDAO.selectApprovalLineByApprovalDocId(approvalDocId);
        List<ApprovalLineListDTO> filteredList = approvalLineLists.stream().filter(approvalLineListDTO ->
            approvalLineListDTO.getOrgUserId() ==orgUserId
        ).collect(Collectors.toList());
        boolean hasApprovalLine = true;
        if(filteredList.isEmpty()) {
            hasApprovalLine = false;
        }else {
            int approverOrder = filteredList.get(0).getApprovalOrder();
            for(int i=0;i<approverOrder-1;i++){
                if(approvalLineLists.get(i).getApprovalStatus() !=ApprovalStatus.APPROVAL.getCode()) {
                    throw new RestApiException(CustomErrorCode.INACTIVE_USER);
                }
            }
        }
        //3.수신참조자 조회
        List<Integer> receivedRefList = approveDAO.selectRecievedRefUserId(approvalDocId);
        boolean hasReceivedRef = receivedRefList.stream().anyMatch(receiveUser ->
                receiveUser == orgUserId
        );
        if(!hasApprovalLine && !sameApprover && !hasReceivedRef) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }

        return true;
    }

    public ApprovalDocDetailDTO showDetailApprovalDoc(int approvalDocId) {
        ApprovalDocDetailDTO approvalDocDetailDTO =  approveDAO.selectApprovalDocById(approvalDocId);
        this.getHasDetailView(approvalDocId, approvalDocDetailDTO);

        approvalDocDetailDTO.setApprovalLineList(approveDAO.selectApprovalDetailLineByApprovalDocId(approvalDocId));
        approvalDocDetailDTO.setReceivedRefList(approveDAO.selectReceivedRefList(approvalDocId));

        return approvalDocDetailDTO;
    }

    @Transactional
    public void updateApprovalDoc(int approvalDocId, ApprovalDocReqDTO approvalDocReqDTO) {
        boolean hasApproval = this.getHasUpdate(approvalDocId);
        if(!hasApproval) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        //1. 결재문서 수정
        approvalDocReqDTO.setApprovalDocId(approvalDocId);
        approvalDocReqDTO.setUpdatedAt(LocalDateTime.now());
        approvalDocReqDTO.setApprovalCount(approvalDocReqDTO.getApproverList().size());
        int affectedCount = approveDAO.updateApprovalDocFromRequest(approvalDocReqDTO);
        if(affectedCount ==0) {
            throw  new RestApiException(CustomErrorCode.APPROVAL_DOC_UPDATE_FAIL);
        }
        //2.결재라인 수정, 결재라인 중 결재가 안된 부분만 수정가능
        this.updateApprovalLine(approvalDocId, approvalDocReqDTO.getApproverList());

        //3.원래 있던 수신참조 삭제 및 수신참조 재삽입
        approveDAO.deleteReceivedRef(approvalDocId);
        List<ReceivedRefDTO> receivedRef = approvalDocReqDTO.getReceiveRefList();
        int totalCount = receivedRef.size();
        int receiveCount = 0;
        for(ReceivedRefDTO dto: receivedRef) {
            dto.setApprovalDocId(approvalDocId);
            receiveCount += approveDAO.insertReceivedRef(dto);
        }
        if(totalCount !=receiveCount) {
            throw new RestApiException(CustomErrorCode.RECEIVED_REF_INSERT_FAIL);
        }

        //4. 알림보내기(문서를 결재한 사람들, 문서를 수신한 사람)
        List<Integer> updateAlarmRecipientList = approveDAO.selectUpdateAlarmRecipient(approvalDocId);
        for(int recipient: updateAlarmRecipientList) {
            alarmService.createNewAlarm(approvalDocId, recipient, AlarmStatus.UPDATE.getCode());
        }
    }

    //결재라인 수정 메서드
    @Transactional
    private void updateApprovalLine(int approvalDocId, List<Integer> approverList) {
        int approvalCount = approverList.size();
        //결재라인 전부 가져오기
        List<ApprovalResDTO> approvalList = approveDAO.selectAllApproval(approvalDocId);

        //만약 리스트가 비어있으면 그냥 결재라인 삽입
        if(approvalList.size() ==0) {
            int insertedCount = this.insertApprovalList(approvalDocId,approverList,0);
            if(insertedCount !=approvalCount) {
                throw new RestApiException(CustomErrorCode.APPROVALLINE_INSERT_FAIL);
            }
        }
        //결재라인에서 approvalStatus 가 P나 W면 수정이 가능한 결재라인임
        int isUpdateOrder = 0; //수정을 시작할 순서
        for(ApprovalResDTO dto: approvalList){
            if(dto.getApprovalStatus() == ApprovalStatus.PROGRESS.getCode()) {
                isUpdateOrder = dto.getApprovalOrder();
            }
        }
        //만약 P라면 결재자가 바뀌었는지 확인하고 바뀌었으면 삭제하고 다시 넣기, 안바뀌었으면 그대로 둘 것(receive_date) 때문에
        if(approvalList.get(isUpdateOrder-1).getOrgUserId() == approverList.get(isUpdateOrder-1)) {
            isUpdateOrder = isUpdateOrder+1;
        }else {
            //알람 보내기
            alarmService.createNewAlarm(approvalDocId,approverList.get(isUpdateOrder-1),AlarmStatus.SUBMIT.getCode());
        }
        //수정가능한 순서가 전체 결재 개수를 넘어간다는 것은 수정할 것이 없다는 것
        if(isUpdateOrder >approvalCount) {
            return;
        }
        //수정할 순서부터 삭제 후 나머지는 재삽입
        int affectedCount = approveDAO.deleteApprovalLine(approvalDocId, isUpdateOrder);

        //재삽입
        this.insertApprovalList(approvalDocId,approverList,isUpdateOrder);
    }

    private int insertApprovalList(int approvalDocId, List<Integer> approverList, int updateOrder) {
            int count = 1;
            for (int approver : approverList) {
                if(count < updateOrder) {
                    count ++;
                    continue;
                }
                PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(approver);
                ApprovalLineDTO approvalLineDTO = new ApprovalLineDTO();
                approvalLineDTO.setApprovalOrder(count);
                if (count == 1) {
                    approvalLineDTO.setApprovalStatus(ApprovalStatus.PROGRESS.getCode());
                    approvalLineDTO.setReceiveDate(LocalDateTime.now());
                    alarmService.createNewAlarm(approvalDocId, approver, AlarmStatus.SUBMIT.getCode());
                } else {
                    approvalLineDTO.setApprovalStatus(ApprovalStatus.WAIT.getCode());
                }
                approvalLineDTO.setApprovalDocId(approvalDocId);
                approvalLineDTO.setOrgUserId(approver);
                approvalLineDTO.setGradeName(positionAndGradeDTO.getGradeName());
                approvalLineDTO.setPositionName(positionAndGradeDTO.getPositionName());
                int affectedCount = approveDAO.insertApprovalLine(approvalLineDTO);
                if (affectedCount == 0) {
                    throw new RestApiException(CustomErrorCode.APPROVALLINE_INSERT_FAIL);
                }
                count++;
            }
            return count;
    }



    @Transactional
    public void removeApprovalDoc(int approvalDocId) {
        boolean hasDelete = this.getHasDelete(approvalDocId);
        if(!hasDelete) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        int affectedCount = approveDAO.deleteApprovalDoc(approvalDocId);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_DOC_DELETE_FAIL);
        }
    }

    public boolean hasPermission(int approvalDocId) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<ApprovalPermissionResDTO> list =  approveDAO.selectApprovalUserIdByApprovalDocId(approvalDocId);
        for(ApprovalPermissionResDTO dto: list) {
            if(dto.getOrgUserId() == orgUserId && dto.getApprovalStatus() ==ApprovalStatus.PROGRESS.getCode()) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void cancelApproval(int approvalDocId) {
        //1.결재문서에서 결재라인에 해당 사용자가 있는지 확인
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<ApprovalLineListDTO> approvalLineLists = approveDAO.selectApprovalLineByApprovalDocId(approvalDocId);
        boolean hasApprovalLine = approvalLineLists.stream().anyMatch(approvalLineListDTO ->
            approvalLineListDTO.getOrgUserId() == orgUserId
        );
        if(!hasApprovalLine) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        //2.있으면 해당 사용자 다음 결재자가 결재하지는 않았는지 확인
        ApprovalLineListDTO currentApprover = null;
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getOrgUserId() ==orgUserId) {
                currentApprover = dto;
            }
        }
        //본인이 아직 결재 안했어도 false
        if(currentApprover.getApprovalStatus() ==ApprovalStatus.WAIT.getCode() || currentApprover.getApprovalStatus() ==ApprovalStatus.PROGRESS.getCode()) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        //3.다음 결재자가 결재했으면 예외
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getApprovalOrder() > currentApprover.getApprovalOrder()
                    && (dto.getApprovalStatus() == ApprovalStatus.APPROVAL.getCode() || dto.getApprovalStatus() == ApprovalStatus.RETURN.getCode())) {
               throw new RestApiException(CustomErrorCode.NEXT_APPROVER_APPROVAL_COMPLETE);
            }
        }
        //4.이전결재자가 있으면 문서상태는 진행중, 아니면 상신중으로 바꿔야 함
        char approvalDocStatus = ApprovalStatus.PROGRESS.getCode();
        if(currentApprover.getApprovalOrder() ==1) {
            approvalDocStatus =ApprovalStatus.WAIT.getCode();
        }
        //결재라인, 결재문서, 품의번호(있으면) 되돌리기
        ApprovalCancelReqDTO approvalCancelReqDTO = new ApprovalCancelReqDTO(orgUserId, approvalDocId,approvalDocStatus);
        int affectedCount = approveDAO.updateApprovalDocToCancel(approvalCancelReqDTO);
        int affecteeCount2 = approveDAO.updateApprovalToCancel(approvalCancelReqDTO);
        //다음사람 결재라인 되돌리기
        int affectedCount3 = approveDAO.updateApprovalNextLine(approvalDocId, currentApprover.getApprovalOrder());

        if(affectedCount ==0 || affecteeCount2 ==0 || affectedCount3 ==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_DOC_UPDATE_FAIL);
        }
    }

    public boolean getHasApproval(int approvalDocId) {
        //1.결재문서에서 결재라인에 해당 사용자가 있는지 확인
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<ApprovalLineListDTO> approvalLineLists = approveDAO.selectApprovalLineByApprovalDocId(approvalDocId);
        boolean hasApprovalLine = approvalLineLists.stream().anyMatch(approvalLineListDTO ->
                approvalLineListDTO.getOrgUserId() == orgUserId
        );
        if(!hasApprovalLine) {
            return false;
        }
        //2.있으면 해당 사용자 다음 결재자가 결재하지는 않았는지 확인
        ApprovalLineListDTO currentApprover = null;
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getOrgUserId() ==orgUserId) {
                currentApprover = dto;
            }
        }
        //본인이 아직 결재 안했어도 false
        if(currentApprover.getApprovalStatus() ==ApprovalStatus.WAIT.getCode() || currentApprover.getApprovalStatus() ==ApprovalStatus.PROGRESS.getCode()) {
            return false;
        }
        //3.다음 결재자가 결재했으면 예외
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getApprovalOrder() > currentApprover.getApprovalOrder()
                    && (dto.getApprovalStatus() == ApprovalStatus.APPROVAL.getCode() || dto.getApprovalStatus() == ApprovalStatus.RETURN.getCode())) {
                return false;
            }
        }
        return true;
    }

    public boolean getHasUpdate(int approvalDocId) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        //1.본인이 상신자인지 확인
        Integer approverId = approveDAO.selectOrgUserIdFromApprovalDoc(approvalDocId);
        if(approverId ==null) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        boolean sameApprover = false;
        if(approverId == orgUserId) {
            sameApprover = true;
        }
        //2.결재문서에서 결재라인에 해당 사용자가 있는지 확인
        List<ApprovalLineListDTO> approvalLineLists = approveDAO.selectApprovalLineByApprovalDocId(approvalDocId);
        boolean hasApprovalLine = approvalLineLists.stream().anyMatch(approvalLineListDTO ->
                approvalLineListDTO.getOrgUserId() == orgUserId
        );
        if(!hasApprovalLine && !sameApprover) {
            return false;
        }
        //3.있으면 해당 사용자 다음 결재자가 결재하지는 않았는지 확인
        ApprovalLineListDTO currentApprover = null;
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getOrgUserId() ==orgUserId) {
                currentApprover = dto;
            }
            if(sameApprover) {
                if(dto.getApprovalOrder()==1) {
                    currentApprover = dto;
                }
            }
        }
        //4.다음 결재자가 결재했으면 예외
        for(ApprovalLineListDTO dto: approvalLineLists) {
            if(dto.getApprovalOrder() > currentApprover.getApprovalOrder()
                    && (dto.getApprovalStatus() == ApprovalStatus.APPROVAL.getCode() || dto.getApprovalStatus() == ApprovalStatus.RETURN.getCode())) {
                return false;
            }
        }
        //5.문서가 종결됐으면 안됨
        Character docStatus = approveDAO.selectApprovalDocStatus(approvalDocId);
        if(docStatus ==null) {
            return true;
        }  else if(docStatus == ApprovalStatus.APPROVAL.getCode() || docStatus == ApprovalStatus.RETURN.getCode()) {
            return false;
        }
        return true;

    }

    public boolean getHasDelete(int approvalDocId) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        //해당 사용자가 문서작성자이면서 결재라인에 있는 첫 결재자가 결재하지 않았을 때에만 삭제가능
        Integer approver = approveDAO.selectOrgUserIdFromApprovalDoc(approvalDocId);
        if(approver ==null) {
            throw new RestApiException(CustomErrorCode.INACTIVE_USER);
        }
        if(approver !=orgUserId) {
            return false;
        }
        Character firstApprovalStatus = approveDAO.selectApprovalDocStatus(approvalDocId);
        if(firstApprovalStatus ==null) {
            return true;
        }  else if(firstApprovalStatus ==ApprovalStatus.APPROVAL.getCode() || firstApprovalStatus == ApprovalStatus.RETURN.getCode()) {
            return false;
        }
        return true;
    }

    @Transactional
    public void updateTemporalApprovalDoc(int approvalDocId, ApprovalDocReqDTO approvalDocReqDTO) {
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<Integer> approverList = approvalDocReqDTO.getApproverList();
        int approvalCount = approverList.size();

        approvalDocReqDTO.setApprovalDocId(approvalDocId);
        approvalDocReqDTO.setOrgUserId(orgUserId);
        int affectedCount = approveDAO.updateTemporalApprovalDoc(approvalDocReqDTO);
        if(affectedCount ==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_DOC_UPDATE_FAIL);
        }

        //결재라인 삭제
        approveDAO.deleteApprovalLine(approvalDocId,0);

        //결재라인 삽입
        int count = this.insertApprovalList(approvalDocId,approverList,0);
        if(count-1 !=approvalCount) {
            throw new RestApiException(CustomErrorCode.APPROVALLINE_INSERT_FAIL);
        }

    }

    @Transactional
    public void insertApprovalAttachment(String S3Url, String fileName, int approvalDocId) {
        ApprovalAttachmentDTO approvalAttachmentDTO = new ApprovalAttachmentDTO();
        approvalAttachmentDTO.setApprovalDocId(approvalDocId);
        approvalAttachmentDTO.setApprovalFilePath(S3Url);
        approvalAttachmentDTO.setFileName(fileName);
        int affectedCount = approveDAO.insertApprovalAttachment(approvalAttachmentDTO);
        if(affectedCount==0) {
            throw new RestApiException(CustomErrorCode.APPROVAL_FAIL);
        }
    }

    public void validPassword(String password) {
        int userId = (int) SessionUtils.getAttribute("userId");
        String currentSalt = userDAO.getSalt(userId);
        String currentPwHash = PasswordUtil.getEncode(password, currentSalt);
        int count = userDAO.selectPasswordByInput(userId, currentPwHash);
        if(count ==0) {
            throw  new RestApiException(CustomErrorCode.INAPPROPIRATE_PASSWORD);
        }
    }

}
