package bitedu.bipa.simplesignbackend.service;

//import bitedu.bipa.simplesignbackend.utils.RedisUtils;
import bitedu.bipa.simplesignbackend.model.dto.FormItemDTO;
import bitedu.bipa.simplesignbackend.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CacheManagerService {
    RedisUtils redisUtils;

    @Autowired
    public CacheManagerService(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    //compItemList
    @KafkaListener(topics = "cdc-test3.simple_sign.common_code", groupId = "consumerGroupId")
    public void kafkaCommonCodeListener(String message) throws JsonProcessingException {
        String redisKey = "compItemList::SimpleKey []";
        redisUtils.deleteData(redisKey);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.form_list", groupId = "consumerGroupId")
    public void kafkaFormListListener(String message) throws JsonProcessingException {
        String redisKey = "formItemList::SimpleKey []";
        redisUtils.deleteData(redisKey);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.company", groupId = "consumerGroupId")
    public void kafkaCompanyListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.establishment", groupId = "consumerGroupId")
    public void kafkaEstablishmentListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.department", groupId = "consumerGroupId")
    public void kafkaDepartmentListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.user", groupId = "consumerGroupId")
    public void kafkaUserListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.organization_user", groupId = "consumerGroupId")
    public void kafkaOrganizationUserListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.grade", groupId = "consumerGroupId")
    public void kafkaGradeListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }

    @KafkaListener(topics = "cdc-test3.simple_sign.position", groupId = "consumerGroupId")
    public void kafkaPositionListener(String message) throws JsonProcessingException {
        String pattern1 = "orgTreeView";
        String pattern2 = "getGrid";
        redisUtils.deleteKeysWithPattern(pattern1);
        redisUtils.deleteKeysWithPattern(pattern2);
    }


}
