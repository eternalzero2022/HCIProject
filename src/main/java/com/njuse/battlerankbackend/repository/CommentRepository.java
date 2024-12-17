package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByCollection_CollectionIdOrderByCreateTimeDesc(Integer cid);
}
