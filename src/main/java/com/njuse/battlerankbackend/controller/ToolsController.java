package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.ImageService;
import com.njuse.battlerankbackend.util.RedisUtil;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ToolsController {

    @Autowired
    ImageService imageService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/images")
    public ResultVO<String> upload(@RequestParam MultipartFile file) {
        return ResultVO.buildSuccess(imageService.upload(file));
    }

    @GetMapping("/counts")
    public ResultVO<Map<String, Integer>> getCounts() {
        return ResultVO.buildSuccess(redisUtil.getAllCountKeys());
    }
}
