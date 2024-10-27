package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.UserService;
import com.njuse.battlerankbackend.vo.ResultVO;
import com.njuse.battlerankbackend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired//自动装载
    UserService userService;

    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody UserVO user) {
        Boolean result = userService.register(user);
        return ResultVO.buildSuccess(result);
    }

    @PostMapping("/login")
    public ResultVO<Boolean> login(@RequestBody UserVO user) {
        Boolean result = userService.login(user);
        return ResultVO.buildSuccess(result);
    }

    @GetMapping()
    public ResultVO<UserVO> getUser() {
        return ResultVO.buildSuccess(userService.getUser());
    }

}
