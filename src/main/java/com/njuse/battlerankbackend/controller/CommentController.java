package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.CommentService;
import com.njuse.battlerankbackend.vo.CommentVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResultVO<Boolean> addComment(@RequestBody CommentVO comment) {
        return ResultVO.buildSuccess(commentService.addComment(comment));
    }

    @GetMapping
    public ResultVO<List<CommentVO>> getCommentsByCollectionId(@RequestParam Integer collectionId) {
        return ResultVO.buildSuccess(commentService.getCommentsByCollectionId(collectionId));
    }

    @DeleteMapping("/{commentId}")
    public ResultVO<Boolean> deleteCommentById(@PathVariable Integer commentId) {
        return ResultVO.buildSuccess(commentService.deleteComment(commentId));
    }
}
