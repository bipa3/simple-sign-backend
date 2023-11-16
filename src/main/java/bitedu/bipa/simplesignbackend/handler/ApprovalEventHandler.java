package bitedu.bipa.simplesignbackend.handler;

import bitedu.bipa.simplesignbackend.event.ApprovalEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

@Service
public class ApprovalEventHandler {

    private final RestTemplate restTemplate;
    private static final String ALARM_SERVICE_URL = "http://ec2-43-202-224-51.ap-northeast-2.compute.amazonaws.com/alarm";

    public ApprovalEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @TransactionalEventListener
    public void handleApprovalEvent(ApprovalEvent approvalEvent) {
        restTemplate.postForObject(ALARM_SERVICE_URL+"/createNewAlarm", approvalEvent, Void.class);
    }

}
