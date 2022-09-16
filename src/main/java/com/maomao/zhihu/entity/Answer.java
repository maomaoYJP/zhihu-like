package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 回答
 * @TableName answer
 */
@TableName(value ="answer")
@Data
public class Answer implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 回答内容
     */
    private String content;

    /**
     * 图片
     */
    private String picture;

    /**
     * 浏览量
     */
    private Long views;

    /**
     * 
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除 1-删除
     */
    @TableLogic
    private Boolean isDelete;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<Comment> comments;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}