package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.AlarmReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalLineDTO;
import bitedu.bipa.simplesignbackend.model.dto.ApprovalReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ReceivedRefDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface AlarmMapper {

    int insertAlarm(AlarmReqDTO dto);

    String selectDefaultMessage(String alarmCode);






}
