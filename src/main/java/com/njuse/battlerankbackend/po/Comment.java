package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.vo.CommentVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "collection_idx", columnList = "cid"))
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cid")
    private CollectionPO collection;

    @Basic
    @Column(length = 511)
    private String comment;

    @Basic
    private Date createTime;

    public CommentVO toVO() {
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(commentId);
        commentVO.setUser(user.toVO());
        commentVO.setCollectionId(collection.getCollectionId());
        commentVO.setComment(comment);
        commentVO.setCreateTime(createTime);
        commentVO.getUser().setPassword("");
        return commentVO;
    }
}
