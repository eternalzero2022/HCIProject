package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.VoteService;
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
    public ResponseEntity<Integer> startVote(@PathVariable Integer collectionId) {
        return ResponseEntity.ok(voteService.startVoteSession(collectionId));
    }

    @GetMapping("/{sessionId}/next")
    public ResponseEntity<VoteRound> nextVoteRound(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(voteService.nextVoteRound(sessionId));
    }

    @PostMapping("/result")
    public ResponseEntity<Boolean> submitVoteRound(@RequestBody VoteRoundResult voteRoundResult) {
        return ResponseEntity.ok(voteService.submitVoteRoundResult(voteRoundResult));
    }

    @GetMapping("/{sessionId}/end")
    public ResponseEntity<Boolean> endVoteRound(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(voteService.endVoteSession(sessionId));
    }
}
