package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.model.dto.AlarmDTO;
import bitedu.bipa.simplesignbackend.service.AlarmService;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @GetMapping("/alarm")
    public List<AlarmDTO> getAlarm(){
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        List<AlarmDTO> alarmDTO = alarmService.selectAlarm(orgUserId);
        return alarmDTO;
    }

    @GetMapping("/alarm/count")
    public int alarmCount(){
        int orgUserId = (int) SessionUtils.getAttribute("orgUserId");
        return alarmService.alarmCount(orgUserId);
    }

    @PutMapping("/alarm/update/{alarmId}")
    public boolean confirmationStatusUpdate(@PathVariable int alarmId) {
        return alarmService.updateConfirmationStatus(alarmId);
    }
}
