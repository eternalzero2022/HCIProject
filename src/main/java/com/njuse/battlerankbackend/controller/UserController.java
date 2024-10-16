package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(HttpSession session, @RequestBody UserVO user) {

        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(HttpSession session, @RequestBody UserVO user) {
        return ResponseEntity.ok(true);
    }

    @GetMapping()
    public ResponseEntity<UserVO> getUser(HttpSession session) {
        return ResponseEntity.ok((UserVO)session.getAttribute("user"));
    }
}
