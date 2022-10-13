package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 评论
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论Id
     */
    private Long parentCommentId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 逻辑删除 1-删除
     */
    private Boolean isDelete;

    /**
     * 评论的用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 评论是否被读过
     */
    @TableField(exist = false)
    private Long isRead;

    /**
     * 该评论的父评论
     */
    @TableField(exist = false)
    private Comment parentComment;

    /**
     * 该评论的所有回复
     * 需要先初始化
     */
    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}