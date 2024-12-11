package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.UserService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import com.njuse.battlerankbackend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired//自动装载
    UserService userService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SecurityUtil securityUtil;

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

    @GetMapping("/{userId}")
    public ResultVO<UserVO> getUserById(@PathVariable Integer userId) {
        return ResultVO.buildSuccess(userService.getUserById(userId));
    }

    @GetMapping("/created")
    public ResultVO<List<CollectionVO>> getUserCreated() {
        return ResultVO.buildSuccess(collectionService.getCollectionsByCreator(securityUtil.getCurrentUser().getUserId()));
    }

    @GetMapping("/voted")
    public ResultVO<List<CollectionVO>> getUserVoted() {
        return ResultVO.buildSuccess(collectionService.getUserVotedCollections(securityUtil.getCurrentUser().getUserId()));
    }
}
