package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.AlarmDAO;
import bitedu.bipa.simplesignbackend.dao.AlarmScheduleDAO;
import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmScheduleService {

    private final AlarmScheduleDAO alarmScheduleDAO;

    public AlarmScheduleService(AlarmScheduleDAO alarmScheduleDAO) {
        this.alarmScheduleDAO = alarmScheduleDAO;
    }


    @Scheduled(cron ="0 0 5 * * ?")
    public void run() {
        System.out.println("Scheduling 시작");

        int pageSize = 100;
        int offset = 0;

        int totalCount = 0;
        while (true) {
            try {
                totalCount = this.partialCopy(offset, pageSize);
                System.out.println("totalCount: " + totalCount);
                if (totalCount == 0) {
                    break;
                }
            } catch (RestApiException e) {
                System.out.println("트랜잭션 중단, 다시 시도");
                this.partialCopy(offset, pageSize);
            }

            offset += totalCount;
        }

        System.out.println("Scheduling 끝");
    }

    @Transactional
    public int partialCopy(int offset, int pageSize) {
        int totalCount = alarmScheduleDAO.selectAllCountAlarmHistory(offset, pageSize);

        if (totalCount == 0) {
            return 0;
        }
        int insertedCount = alarmScheduleDAO.insertAlarmDeleteHistory(offset, pageSize);
        if(totalCount != insertedCount) {
            throw new RestApiException(CustomErrorCode.ALARM_SCHEDULING_INSERT_FAIL);
        }
        int deletedCount = alarmScheduleDAO.deleteAlarmHistory(offset, pageSize);

        if (deletedCount != pageSize) {
            throw new RestApiException(CustomErrorCode.ALARM_SCHEDULING_DELETE_FAIL);
        }
        return totalCount;
    }
}
