package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    private Integer commentId;

    private UserVO user;

    private Integer collectionId;

    private String comment;

    private Date createTime;

}
