package com.njuse.battlerankbackend.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TakeCountAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Before("@annotation(takeCount)")
    public void doBefore(TakeCount takeCount) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String url = request.getRequestURL().toString();

        String key = url + ":count";
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().increment(key, 1);
        } else {
            redisTemplate.opsForValue().set(key, 1);
        }
    }
}
