package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.enums.AlarmStatus;
import bitedu.bipa.simplesignbackend.enums.ApprovalStatus;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApproveService {

    private final ApproveDAO approveDAO;
    private final CommonDAO commonDAO;
    private final AlarmService alarmService;
    private final SequenceService sequenceService;


    public ApproveService(ApproveDAO approveDAO, CommonDAO commonDAO, AlarmService alarmService, SequenceService sequenceService) {
        this.approveDAO = approveDAO;
        this.commonDAO = commonDAO;
        this.alarmService = alarmService;
        this.sequenceService = sequenceService;
    }

    @Transactional
    public void  registerApprovalDoc(ApprovalDocReqDTO approvalDocReqDTO, int userId) {
        List<Integer> approverList = approvalDocReqDTO.getApproverList();
        int approvalCount = approverList.size();

        approvalDocReqDTO.setApprovalCount(approvalCount);
        int approvalDocId =approveDAO.insertApprovalDoc(approvalDocReqDTO, userId);


        //docStatus 가 상신인지 임시저장인지 확인
        if(approvalDocReqDTO.getDocStatus() == ApprovalStatus.TEMPORARY.getCode()) {
            //임시저장이면 첨부파일 insert

        }else if(approvalDocReqDTO.getDocStatus() == ApprovalStatus.WAIT.getCode()) {
            //상신이면 approval, alarm, 첨부파일 insert

            int count = 1;
            for(int approver: approverList) {
                PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(approver);
                ApprovalLineDTO approvalLineDTO = new ApprovalLineDTO();
                approvalLineDTO.setApprovalOrder(count);
                if(count ==1) {
                    approvalLineDTO.setApprovalStatus(ApprovalStatus.PROGRESS.getCode());
                    approvalLineDTO.setReceiveDate(LocalDateTime.now());
                    alarmService.createNewAlarm(approvalDocId,approver, AlarmStatus.SUBMIT.getCode());
                }else {
                    approvalLineDTO.setApprovalStatus(ApprovalStatus.WAIT.getCode());
                }
                approvalLineDTO.setApprovalDocId(approvalDocId);
                approvalLineDTO.setUserId(approver);
                approvalLineDTO.setDeptId(positionAndGradeDTO.getDeptId());
                approvalLineDTO.setGradeName(positionAndGradeDTO.getGradeName());
                approvalLineDTO.setPositionName(positionAndGradeDTO.getPositionName());
                int affectedCount = approveDAO.insertApprovalLine(approvalLineDTO);
                if(affectedCount ==0) {
                    throw  new RuntimeException();
                }
                count++;
            }
        }
    }

    @Transactional
    public void approveApprovalDoc(int userId, int approvalDocId) {
        //결재하기
        //1. 결재테이블에서 결재할 문서가 있는지 가져오기, 없으면 bad request
        ApprovalResDTO approvalResDTO =  approveDAO.selectApprovalByApprovalId(userId,approvalDocId);
        if(approvalResDTO.getApprovalId() ==0 || approvalResDTO.getApprovalStatus() !='P') {
            throw  new RuntimeException();
        }else {
            //2. 결재문서가 있다면 결재 상태를 '승인'으로 바꾸고 결재시간 삽입하기
            approvalResDTO.setApprovalStatus(ApprovalStatus.APPROVAL.getCode());
            approvalResDTO.setApprovalDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateCurrentApproval(approvalResDTO);
            if(affectedCount ==0) {
                throw new RuntimeException();
            }
        }

        //3. 결재승인자의 결재순서와 결재문서의 결재 개수가 같은지 확인하고 같다면 결재문서도 '승인'상태로 바꿔주고 결재 종결
        ApprovalDocResDTO approvalDocResDTO = approveDAO.selectApprovalCount(approvalDocId);
        boolean isApprovalDocEnd = approvalDocResDTO.getApprovalCount() == approvalResDTO.getApprovalOrder();
        int upperApproverId = 0;
        if(isApprovalDocEnd) {
            approvalDocResDTO.setDocStatus(ApprovalStatus.APPROVAL.getCode());
            String productNumber = sequenceService.createProductNum(approvalDocResDTO.getSeqCode(), approvalDocResDTO.getUserId());
            approvalDocResDTO.setProductNum(productNumber);

            //수신참조 insert --------------------추후 삽입 예정
        }else {
            //같지 않다면 '진행'으로 바꾸고 상신 상위자의 결재테이블 상태도 '진행'으로, 결재수신시간도 넣어주기
            approvalDocResDTO.setDocStatus(ApprovalStatus.PROGRESS.getCode());
            ApprovalResDTO upperApproverDTO = approveDAO.selectUpperApproverId(approvalResDTO);
            upperApproverId = upperApproverDTO.getUserId();
            upperApproverDTO.setApprovalStatus(ApprovalStatus.PROGRESS.getCode());
            upperApproverDTO.setReceiveDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateUpperApproverId(upperApproverDTO);
            if(affectedCount ==0) {
                throw  new RuntimeException();
            }
        }
        int affectedCount = approveDAO.updateApprovalDoc(approvalDocResDTO);
        if(affectedCount ==0) {
            throw new RuntimeException();
        }

        //4. 알림보내기(결재승인알람 및 결재문서가 종결이라면 종결알람)
        if(isApprovalDocEnd) {
            //종결일 경우는 상신자에게 알림
            alarmService.createNewAlarm(approvalDocId, approvalDocResDTO.getUserId(),AlarmStatus.APPROVE.getCode());
        }else {
            //미종결일 경우는 상위자에게 알림
            alarmService.createNewAlarm(approvalDocId,upperApproverId,AlarmStatus.SUBMIT.getCode());
        }
    }

    @Transactional
    public void returnApprovalDoc(int userId, int approvalDocId) {
        //1. 결재테이블에서 결재할 문서가 있는지 가져오기, 없으면 bad request
        //결재하기
        ApprovalResDTO approvalResDTO =  approveDAO.selectApprovalByApprovalId(userId,approvalDocId);
        if(approvalResDTO.getApprovalId() ==0 || approvalResDTO.getApprovalStatus() !='P') {
            throw  new RuntimeException();
        }else {
            //2. 결재문서가 있다면 결재상태를 '반려'로 바꾸고 결재시간 삽입하기
            approvalResDTO.setApprovalStatus(ApprovalStatus.RETURN.getCode());
            approvalResDTO.setApprovalDate(LocalDateTime.now());
            int affectedCount = approveDAO.updateCurrentApproval(approvalResDTO);
            if(affectedCount ==0) {
                throw new RuntimeException();
            }
        }
        //3. 결재 문서를 '반려' 상태로 바꾸기(이후 결재자 행은 어떻게 할건지?)
        ApprovalDocResDTO approvalDocResDTO = new ApprovalDocResDTO();
        approvalDocResDTO.setDocStatus(ApprovalStatus.RETURN.getCode());
        approvalDocResDTO.setApprovalDocId(approvalDocId);
        int affectedCount = approveDAO.updateApprovalDoc(approvalDocResDTO);
        if(affectedCount ==0) {
            throw new RuntimeException();
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

    public ApprovalDocDetailDTO showDetailApprovalDoc(int approvalDocId) {
        ApprovalDocDetailDTO approvalDocDetailDTO =  approveDAO.selectApprovalDocById(approvalDocId);
        return approvalDocDetailDTO;
    }


//            //수신참조 insert ---> 활성화 여부를 넣고 활성화 된 것만 불러와서 보여주게?
//            List<Integer> receivedRef = approvalReqDTO.getReceiveRefList();
//            int totalCount = receivedRef.size();
//            int receiveCount = 0;
//            for(int receiver: receivedRef) {
//               int deptId = commonDAO.selectDeptId(receiver);
//                ReceivedRefDTO receivedRefDTO = new ReceivedRefDTO(receiver,deptId,approvalDocId);
//                int affectedCount = approveDAO.insertReceivedRef(receivedRefDTO);
//                createNewAlarm(approvalDocId,receiver,AlarmStatus.ReceivedRef.getCode());
//                receiveCount +=affectedCount;
//            }
//            if(totalCount !=receiveCount) {
//                throw new RuntimeException();
//            }

}
