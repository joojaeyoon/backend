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
    ValueOperations<String, String> values;

    public RedisUtil() {
        assert redisTemplate != null;
        values=redisTemplate.opsForValue();
    }

    public String get(String key){
        return values.get(key);
    }

    public void set(String key,String value){
        values.set(key,value);
    }
    public void setDataExpire(String key,String value,long duration){
        Duration expire=Duration.ofSeconds(duration);
        values.set(key,value,expire);
    }
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
