package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.CommentVO;

import java.util.List;

public interface CommentService {
    List<CommentVO> getCommentsByCollectionId(Integer collectionId);

    Boolean addComment(CommentVO commentVO);

    Boolean deleteComment(Integer commentId);
}
