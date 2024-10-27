package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.ImageService;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ToolsController {

    @Autowired
    ImageService imageService;

    @PostMapping("/images")
    public ResultVO<String> upload(@RequestParam MultipartFile file) {
        return ResultVO.buildSuccess(imageService.upload(file));
    }
}
