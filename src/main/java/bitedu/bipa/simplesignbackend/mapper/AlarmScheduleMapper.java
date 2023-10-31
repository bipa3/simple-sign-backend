package bitedu.bipa.simplesignbackend.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmScheduleMapper {

    int insertAlarmDeleteHistory(int offset, int pageSize);

    int deleteAlarmHistory(int offset, int pageSize);

    int selectAllCountAlarmHistory(int offset, int pageSize);
}
