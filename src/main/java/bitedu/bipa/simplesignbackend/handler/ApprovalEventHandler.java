package bitedu.bipa.simplesignbackend.handler;

import bitedu.bipa.simplesignbackend.event.ApprovalEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class ApprovalEventHandler {

    private final RestTemplate restTemplate;
    private static final String ALARM_SERVICE_URL = "http://ec2-43-202-224-51.ap-northeast-2.compute.amazonaws.com/alarm";

    public ApprovalEventHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @TransactionalEventListener
    public void handleApprovalEvent(ApprovalEvent approvalEvent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ApprovalEvent> entity = new HttpEntity<>(approvalEvent,headers);

       try {
           restTemplate.postForObject(ALARM_SERVICE_URL + "/createNewAlarm", entity, Void.class);
       }catch (Exception e) {
           System.out.println(e);
       }
    }
}
