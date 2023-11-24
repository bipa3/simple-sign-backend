package bitedu.bipa.simplesignbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtils {
    private final RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 데이터 삽입
    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 데이터 읽기
    public Object readData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object readHashData(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // 데이터 수정
    public void updateData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 데이터 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public void deleteKeysWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys("*" + pattern + "*");
        redisTemplate.delete(keys);
    }
}
