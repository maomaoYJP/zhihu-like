package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName comment_tip
 */
@TableName(value ="comment_tip")
@Data
public class CommentTip implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long commentId;

    /**
     * 
     */
    private Long passageId;

    /**
     * 
     */
    private Long answerId;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long isRead;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}