package dev.jooz.Web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public RedisUtil() {}

    public String get(String key){
        ValueOperations<String, String> values=redisTemplate.opsForValue();
        return values.get(key);
    }

    public void set(String key,String value){
        ValueOperations<String, String> values=redisTemplate.opsForValue();
        values.set(key,value);
    }
    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String, String> values=redisTemplate.opsForValue();
        Duration expire=Duration.ofSeconds(duration);
        values.set(key,value,expire);
    }
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
