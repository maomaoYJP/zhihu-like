package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
    private Boolean isDelete;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}