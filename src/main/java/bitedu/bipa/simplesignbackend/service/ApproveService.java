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


    public ApproveService(ApproveDAO approveDAO, CommonDAO commonDAO, AlarmService alarmService) {
        this.approveDAO = approveDAO;
        this.commonDAO = commonDAO;
        this.alarmService = alarmService;
    }

    @Transactional
    public void  registerApprovalDoc(ApprovalReqDTO approvalReqDTO, int userId) {
        List<Integer> approverList = approvalReqDTO.getApproverList();
        int approvalCount = approverList.size();

        approvalReqDTO.setApprovalCount(approvalCount);
        int approvalDocId =approveDAO.insertApprovalDoc(approvalReqDTO, userId);


        //docStatus 가 상신인지 임시저장인지 확인
        if(approvalReqDTO.getDocStatus() == ApprovalStatus.TEMPORARY.getCode()) {
            //임시저장이면 첨부파일 insert

        }else if(approvalReqDTO.getDocStatus() == ApprovalStatus.WAIT.getCode()) {
            //상신이면 approval, alarm, 첨부파일 insert

            int count = 1;
            for(int approver: approverList) {
                PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(approver);
                ApprovalLineDTO approvalLineDTO = new ApprovalLineDTO();
                approvalLineDTO.setApprovalOrder(count);
                if(count ==1) {
                    approvalLineDTO.setApprovalStatus(ApprovalStatus.PROGRESS.getCode());
                    approvalLineDTO.setReceiveDate(LocalDateTime.now());
                    alarmService.createNewAlarm(approvalDocId,approver, AlarmStatus.Submit.getCode());
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


//            //품의번호 insert
//            int seqCode = approvalReqDTO.getSeqCode();
//            int formCode = approvalReqDTO.getFormCode();
//            createProductNum(seqCode, formCode);


//            //수신참조 insert
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
