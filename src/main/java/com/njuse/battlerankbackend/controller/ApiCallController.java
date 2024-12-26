package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.ApiCallListenService;
import com.njuse.battlerankbackend.service.ApiCallService;
import com.njuse.battlerankbackend.vo.ApiCallRecordVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apicall")
public class ApiCallController {
    @Autowired
    private ApiCallService apiCallService;
    
    @GetMapping
    public ResultVO<List<ApiCallRecordVO>> getApiUsage() {
        //从redisTemplate中获取到数据
        return ResultVO.buildSuccess(apiCallService.getApiUsage());
    }
}
