package com.njuse.battlerankbackend.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njuse.battlerankbackend.service.ApiCallListenService;
import com.njuse.battlerankbackend.po.ApiCallRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ApiCallListenServiceImpl implements ApiCallListenService {
    @Autowired
    private RedisTemplate<String, ApiCallRecord> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(Example.class);
    @Override
    @KafkaListener(topics = "api-call-record", groupId = "api-call-record-listener")
    public void listen(String apiCallMessage) {
        System.out.printf("ApiCallRecordListener Consumed message: %s%n", apiCallMessage);
        try{
            ApiCallRecord record = objectMapper.readValue(apiCallMessage, ApiCallRecord.class);
            redisTemplate.opsForZSet().incrementScore("api:call-count",record, 1);

        }catch (Exception e){
            logger.error("An error occurred: ", e); // 使用日志记录异常
        }
    }
}
