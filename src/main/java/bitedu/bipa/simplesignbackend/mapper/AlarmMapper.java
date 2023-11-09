package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.AlarmDTO;
import bitedu.bipa.simplesignbackend.model.dto.AlarmReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.UserApprovalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AlarmMapper {

    int insertAlarm(AlarmReqDTO dto);

    String selectDefaultMessage(String alarmCode);

    // 전체 알림 들고오기
    List<AlarmDTO> alarmSelect(int orgUserId);

    // 문서명
    UserApprovalDTO selectApprovalDocTitle(@Param("alarmDate") String alarmDate, @Param("orgUserId") int orgUSerId,
                                           @Param("alarmCode") String alarmCode, @Param("approvalDocId") int approvalDocId);

    // 결재자
    UserApprovalDTO selectApprovalUserName(int alarmId);

    // 안읽은 알림 총 갯수
    int AlarmCount(int orgUserId);

    // 알림 읽음 처리
    boolean updateConfirmationStatus(int alarmId);

}
