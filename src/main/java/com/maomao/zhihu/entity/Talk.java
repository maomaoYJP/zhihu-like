package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 说说
 * @TableName talk
 */
@TableName(value ="talk")
@Data
public class Talk implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 说说内容
     */
    private String content;

    /**
     * 
     */
    private Date createTime;

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