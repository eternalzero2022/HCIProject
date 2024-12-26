package com.njuse.battlerankbackend.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njuse.battlerankbackend.po.ApiCallRecord;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TakeCountAspect {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(Example.class);


    @Before("@annotation(takeCount)")
    public void doBefore(TakeCount takeCount) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String url = request.getRequestURL().toString();
        
        String method = request.getMethod().toString();

        try {
            ApiCallRecord apiCallRecord = new ApiCallRecord(url,method);
            String message = objectMapper.writeValueAsString(apiCallRecord);
            kafkaTemplate.send("api-call-record",message);
        }catch (Exception e){
            logger.error("记录api调用信息失败",e);
        }
    }
}
