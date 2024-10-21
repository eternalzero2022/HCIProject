package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.UserService;
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
    public ResponseEntity<Boolean> register( @RequestBody UserVO user) {
        Boolean result = userService.register(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserVO user) {
        Boolean result = userService.login(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<UserVO> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

}
