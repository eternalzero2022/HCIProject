package com.njuse.battlerankbackend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Integer> getAllCountKeys() {
        Map<String, Integer> result = new HashMap<>();

        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            Cursor<byte[]> cursor = connection.scan(
                    ScanOptions.scanOptions().match("*:count").build()
            );
            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                Integer value = (Integer) redisTemplate.opsForValue().get(key);
                result.put(key, value);
            }
            return null;
        });

        return result;
    }
}
