package bitedu.bipa.simplesignbackend.handler;

import bitedu.bipa.simplesignbackend.event.ApprovalEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class ApprovalEventHandler {


    private final KafkaTemplate<String, ApprovalEvent> kafkaTemplate;

//    private final RestTemplate restTemplate;
//    private static final String ALARM_SERVICE_URL = "http://ec2-43-202-224-51.ap-northeast-2.compute.amazonaws.com/alarm";

    public ApprovalEventHandler(KafkaTemplate kafkaTemplate) {
        //this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }


    @EventListener
    @Async
    public void handleApprovalEvent(ApprovalEvent approvalEvent) {
        try {
            kafkaTemplate.send("alarmTopic", approvalEvent);
        }catch (Exception e) {
            log.info(String.valueOf(e));
        }
//        restTemplate.getInterceptors().add((request, body, execution) -> {
//            ClientHttpResponse response = execution.execute(request,body);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return response;
//        });
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ApprovalEvent> entity = new HttpEntity<>(approvalEvent,headers);
//
//        try {
//            ResponseEntity<Void> response = restTemplate.exchange(
//                    ALARM_SERVICE_URL + "/createNewAlarm",
//                    HttpMethod.POST,
//                    entity,
//                    Void.class
//            );
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
