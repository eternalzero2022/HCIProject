package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.po.ApiCallRecord;
import com.njuse.battlerankbackend.service.ApiCallService;
import com.njuse.battlerankbackend.vo.ApiCallRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ApiCallServiceImpl implements ApiCallService {
    @Autowired
    private RedisTemplate<String, ApiCallRecord> redisTemplate;

    @Override
    public List<ApiCallRecordVO> getApiUsage() {
        Set<ZSetOperations.TypedTuple<ApiCallRecord>> results = redisTemplate.opsForZSet().reverseRangeWithScores("api:call-count", 0, -1);
        List<ApiCallRecordVO> usageList = new ArrayList<ApiCallRecordVO>();
        if (results != null) {
            for (ZSetOperations.TypedTuple<ApiCallRecord> result : results) {
                ApiCallRecord apiCallRecord = result.getValue();
                Long count = Objects.requireNonNull(result.getScore()).longValue();
                if (apiCallRecord != null) {
                    usageList.add(new ApiCallRecordVO(apiCallRecord.getUrl(), apiCallRecord.getMethod(), count));
                }
            }
        }
        return usageList;
    }
}
