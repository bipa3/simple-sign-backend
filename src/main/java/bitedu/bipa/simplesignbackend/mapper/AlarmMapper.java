package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.AlarmReqDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AlarmMapper {

    int insertAlarm(AlarmReqDTO dto);

    String selectDefaultMessage(String alarmCode);






}
