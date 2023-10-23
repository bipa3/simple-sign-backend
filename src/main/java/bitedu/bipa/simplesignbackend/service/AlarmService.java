package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AlarmDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.AlarmReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.PositionAndGradeDTO;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlarmService {

    private AlarmDAO alarmDAO;
    private CommonDAO commonDAO;

    public AlarmService (AlarmDAO alarmDAO, CommonDAO commonDAO) {
        this.alarmDAO = alarmDAO;
        this.commonDAO = commonDAO;
    }

    public void createNewAlarm(int approvalDocId, int orgUserId, String alarmCode) {
        PositionAndGradeDTO positionAndGradeDTO = commonDAO.getPositionAndGrade(orgUserId);
        AlarmReqDTO alarmReqDTO = new AlarmReqDTO();
        alarmReqDTO.setAlarmCode(alarmCode);
        alarmReqDTO.setAlarmDate(LocalDateTime.now());
        alarmReqDTO.setOrgUserId(orgUserId);
        alarmReqDTO.setGradeName(positionAndGradeDTO.getGradeName());
        alarmReqDTO.setPositionName(positionAndGradeDTO.getPositionName());
        alarmReqDTO.setApprovalDocId(approvalDocId);

        String defaultMessage = alarmDAO.selectDefaultMessage(alarmCode);
        //조건에 따른 알람 메세지 넣기(추후 수정)
        alarmReqDTO.setAlarmContent(defaultMessage);
        int affectedCount = alarmDAO.insertAlarm(alarmReqDTO);
        if(affectedCount ==0) {
            throw  new RestApiException(CustomErrorCode.ALARM_INSERT_FAIL);
        }
    }
}
