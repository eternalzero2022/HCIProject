package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.VoteService;
import com.njuse.battlerankbackend.vo.ResultVO;
import com.njuse.battlerankbackend.vo.VoteRound;
import com.njuse.battlerankbackend.vo.VoteRoundResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/start/{collectionId}")
    public ResultVO<Integer> startVote(@PathVariable Integer collectionId) {
        return ResultVO.buildSuccess(voteService.startVoteSession(collectionId));
    }

    @GetMapping("/{sessionId}/next")
    public ResultVO<VoteRound> nextVoteRound(@PathVariable Integer sessionId) {
        return ResultVO.buildSuccess(voteService.nextVoteRound(sessionId));
    }

    @PostMapping("/result")
    public ResultVO<Boolean> submitVoteRound(@RequestBody VoteRoundResult voteRoundResult) {
        return ResultVO.buildSuccess(voteService.submitVoteRoundResult(voteRoundResult));
    }

    @GetMapping("/{sessionId}/end")
    public ResultVO<Boolean> endVoteRound(@PathVariable Integer sessionId) {
        return ResultVO.buildSuccess(voteService.endVoteSession(sessionId));
    }

    @GetMapping("/{sessionId}/exclude")
    public ResultVO<Boolean> excludeItem(@PathVariable Integer sessionId, @RequestParam Integer itemId) {
        return ResultVO.buildSuccess(voteService.excludeItem(sessionId, itemId));
    }
}
