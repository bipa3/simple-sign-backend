package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AlarmDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.AlarmDTO;
import bitedu.bipa.simplesignbackend.model.dto.AlarmReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.PositionAndGradeDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserApprovalDTO;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlarmService {

    private AlarmDAO alarmDAO;
    private CommonDAO commonDAO;
    private SimpMessagingTemplate messagingTemplate;

    public AlarmService (AlarmDAO alarmDAO, CommonDAO commonDAO, SimpMessagingTemplate messagingTemplate) {
        this.alarmDAO = alarmDAO;
        this.commonDAO = commonDAO;
        this.messagingTemplate = messagingTemplate;
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
//        if(affectedCount ==0) {
//            throw  new RestApiException(CustomErrorCode.ALARM_INSERT_FAIL);
//        }

        if(affectedCount > 0){
            this.sendMeassge(alarmReqDTO);
        }else{
            throw  new RestApiException(CustomErrorCode.ALARM_INSERT_FAIL);
        }
    }

    public void sendMeassge(AlarmReqDTO alarmReqDTO){
        LocalDateTime alarmDate = alarmReqDTO.getAlarmDate();
        System.out.println(alarmDate);
        alarmDate = alarmDate.truncatedTo(ChronoUnit.SECONDS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = alarmDate.format(formatter);
        UserApprovalDTO userApprovalDto = alarmDAO.selectApprovalDocTitle(formattedDate, alarmReqDTO.getOrgUserId(), alarmReqDTO.getAlarmCode(), alarmReqDTO.getApprovalDocId());
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setAlarmCode(alarmReqDTO.getAlarmCode());
        alarmDTO.setAlarmDate(formattedDate);
        alarmDTO.setAlarmContent(alarmReqDTO.getAlarmContent());
        alarmDTO.setApprovalDocId(alarmReqDTO.getApprovalDocId());
        alarmDTO.setApprovalDocTitle(userApprovalDto.getApprovalDocTitle());
        alarmDTO.setAlarmId(userApprovalDto.getAlarmId());
        messagingTemplate.convertAndSend("/topic/alarm/" + alarmReqDTO.getOrgUserId(), alarmDTO);
    }

    // 전체 알림을 들고오는 부분
    public List<AlarmDTO> selectAlarm(int orgUserId) {

        List<AlarmDTO> alarmDTOList = alarmDAO.selectAlarm(orgUserId);

        for(AlarmDTO alarmDTO : alarmDTOList) {
            int alarmId = alarmDTO.getAlarmId();

            UserApprovalDTO userApprovalDTO = alarmDAO.selectApprovalUserName(alarmId);

            if(userApprovalDTO != null) {
                alarmDTO.setApprovalCount(userApprovalDTO.getApprovalCount());
                alarmDTO.setUserName(userApprovalDTO.getUserName());
            }
        }
        return alarmDTOList;
    }

    // 알림의 갯수를 구함
    public int alarmCount(int orgUserId){
        return alarmDAO.alarmCount(orgUserId);
    }

    // 알림의 읽음으로 변경
    public boolean updateConfirmationStatus(int alarmId){
        return alarmDAO.updateConfirmationStatus(alarmId);
    }

}
