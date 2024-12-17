package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.Comment;
import com.njuse.battlerankbackend.repository.CommentRepository;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.CommentService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<CommentVO> getCommentsByCollectionId(Integer collectionId) {
        return commentRepository.findByCollection_CollectionIdOrderByCreateTimeDesc(collectionId).stream()
                .map(Comment::toVO)
                .toList();
    }

    @Override
    public Boolean addComment(CommentVO commentVO) {
        CollectionPO collection = collectionService.getCollectionPO(commentVO.getCollectionId());
        if (collection == null) {
            throw SelfDefineException.getCollectionFault();
        }
        Comment comment = new Comment();
        comment.setCommentId(0);
        comment.setUser(securityUtil.getCurrentUser());
        comment.setCollection(collection);
        comment.setComment(commentVO.getComment());
        comment.setCreateTime(new Date());
        commentRepository.save(comment);
        return true;
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }
}
