package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AlarmDAO;
import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

@Service
public class ApproveService {

    private final ApproveDAO approveDAO;
    private final CommonDAO commonDAO;
    private final AlarmDAO alarmDAO;

    public ApproveService(ApproveDAO approveDAO, CommonDAO commonDAO, AlarmDAO alarmDAO) {
        this.approveDAO = approveDAO;
        this.commonDAO = commonDAO;
        this.alarmDAO = alarmDAO;
    }

    @Transactional
    public void  registerApprovalDoc(ApprovalReqDTO approvalReqDTO, int userId) {
        //approval insert
        List<Integer> approverList = approvalReqDTO.getApproverList();
        int approvalCount = approverList.size();

        approvalReqDTO.setApprovalCount(approvalCount);
        int approvalDocId =approveDAO.insertApprovalDoc(approvalReqDTO, userId);


        //docStatus 가 상신인지 임시저장인지 확인
        if(approvalReqDTO.getDocStatus() =='T') {
            //임시저장이면 첨부파일, 알림 insert
            createNewAlarm(approvalDocId,userId,"00");

        }else if(approvalReqDTO.getDocStatus() =='W') {
            //상신이면 approval, 수신참조,  alarm, 품의번호이력, 첨부파일 insert
            createNewAlarm(approvalDocId, userId,"01");

            int count = 1;
            for(int approver: approverList) {
                PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(approver);
                ApprovalLineDTO approvalLineDTO = new ApprovalLineDTO();
                approvalLineDTO.setApprovalOrder(count);
                if(count ==1) {
                    approvalLineDTO.setApprovalStatus('A');
                    approvalLineDTO.setReceiveDate(LocalDateTime.now());
                    approvalLineDTO.setApprovalDate(LocalDateTime.now());
                }else if(count ==2) {
                    approvalLineDTO.setApprovalStatus('P');
                    approvalLineDTO.setReceiveDate(LocalDateTime.now());
                    createNewAlarm(approvalDocId,approver,"02");
                }else {
                    approvalLineDTO.setApprovalStatus('W');
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
            //품의번호 insert
            int seqCode = approvalReqDTO.getSeqCode();
            int formCode = approvalReqDTO.getFormCode();
            createProductNum(seqCode, formCode);


            //수신참조 insert
            List<Integer> receivedRef = approvalReqDTO.getReceiveRefList();
            int totalCount = receivedRef.size();
            int receiveCount = 0;
            for(int receiver: receivedRef) {
               int deptId = commonDAO.selectDeptId(receiver);
                ReceivedRefDTO receivedRefDTO = new ReceivedRefDTO(receiver,deptId,approvalDocId);
                int affectedCount = approveDAO.insertReceivedRef(receivedRefDTO);
                createNewAlarm(approvalDocId,receiver,"12");
                receiveCount +=affectedCount;
            }
            if(totalCount !=receiveCount) {
                throw new RuntimeException();
            }

        }
    }

    private void createNewAlarm(int approvalDocId, int userId, String alarmCode) {
        PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(userId);
        AlarmReqDTO alarmReqDTO = new AlarmReqDTO();
        alarmReqDTO.setAlarmCode(alarmCode);
        alarmReqDTO.setAlarmDate(LocalDateTime.now());
        alarmReqDTO.setUserId(userId);
        alarmReqDTO.setDeptId(positionAndGradeDTO.getDeptId());
        alarmReqDTO.setGradeName(positionAndGradeDTO.getGradeName());
        alarmReqDTO.setPositionName(positionAndGradeDTO.getPositionName());
        alarmReqDTO.setApprovalDocId(approvalDocId);

        String defaultMessage = alarmDAO.selectDefaultMessage(alarmCode);
        //조건에 따른 알람 메세지 넣기(추후 수정)
        alarmReqDTO.setAlarmContent(defaultMessage);
        int affectedCount = alarmDAO.insertAlarm(alarmReqDTO);
        if(affectedCount ==0) {
            throw  new RuntimeException();
        }
    }

    private void createProductNum(int seqCode, int formCode) {
        int recentProductNum = approveDAO.selectMaxProductNumber(seqCode, formCode);
        int affectedCount = 0;

        StringBuffer buffer = returnProductFullName(seqCode,recentProductNum);
        SequenceUseFormDTO sequenceUseFormDTO = new SequenceUseFormDTO(seqCode,formCode,buffer.toString(),recentProductNum+1);
        affectedCount = approveDAO.insertProductNumber(sequenceUseFormDTO);
        if(affectedCount ==0) {
            throw  new RuntimeException();
        }
    }

    private StringBuffer returnProductFullName(int seqCode, int recentProductNum) {
        List<ProductNumberDTO> sequenceFormList = approveDAO.selectSequenceForm(seqCode);
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<sequenceFormList.size();i++) {
            ProductNumberDTO dto = sequenceFormList.get(i);
            if(dto.getSeqFormOrder()==i+1) {
                String codeValue = dto.getCodeValue();
                if(codeValue.equals("자리수")) {
                    if(recentProductNum ==0) {
                        codeValue = "1";
                    }else {
                        codeValue = String.valueOf(recentProductNum+1);
                    }
                }else if(codeValue.equals("년도")) {
                    codeValue = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY"));
                }
                buffer.append(codeValue);
            }
        }
        return buffer;
    }

}
