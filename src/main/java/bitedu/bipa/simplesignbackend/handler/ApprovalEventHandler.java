package bitedu.bipa.simplesignbackend.handler;

import bitedu.bipa.simplesignbackend.event.ApprovalEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

@Service
public class ApprovalEventHandler {

    private final RestTemplate restTemplate;
    private static final String ALARM_SERVICE_URL = "http://localhost:8081";

    public ApprovalEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @TransactionalEventListener
    public void handleApprovalEvent(ApprovalEvent approvalEvent) {
        restTemplate.postForObject(ALARM_SERVICE_URL+"/createNewAlarm", approvalEvent, Void.class);
    }

}
