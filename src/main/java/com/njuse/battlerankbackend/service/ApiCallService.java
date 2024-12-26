package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.ApiCallRecordVO;

import java.util.List;

public interface ApiCallService {
    public List<ApiCallRecordVO> getApiUsage();
}
