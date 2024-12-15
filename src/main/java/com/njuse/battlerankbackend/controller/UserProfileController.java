package com.njuse.battlerankbackend.controller;


import com.njuse.battlerankbackend.service.UserProfileService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/created")
    public ResultVO<List<CollectionVO>> getCreatedCollections() {
        return ResultVO.buildSuccess(userProfileService.getCreatedCollections(securityUtil.getCurrentUser().getUserId()));
    }

    @GetMapping("/voted")
    public ResultVO<List<CollectionVO>> getVotedCollections() {
        return ResultVO.buildSuccess(userProfileService.getVotedCollections(securityUtil.getCurrentUser().getUserId()));
    }

    @GetMapping("/favorites")
    public ResultVO<List<CollectionVO>> getFavoriteCollections() {
        return ResultVO.buildSuccess(userProfileService.getFavouriteCollections(securityUtil.getCurrentUser().getUserId()));
    }

    @GetMapping("/{userId}/created")
    public ResultVO<List<CollectionVO>> getCreatedCollection(@PathVariable Integer userId) {
        return ResultVO.buildSuccess(userProfileService.getCreatedCollections(userId));
    }

    @GetMapping("/{userId}/voted")
    public ResultVO<List<CollectionVO>> getVotedCollection(@PathVariable Integer userId) {
        return ResultVO.buildSuccess(userProfileService.getVotedCollections(userId));
    }

    @GetMapping("/{userId}/favorites")
    public ResultVO<List<CollectionVO>> getFavoriteCollection(@PathVariable Integer userId) {
        return ResultVO.buildSuccess(userProfileService.getFavouriteCollections(userId));
    }
}
