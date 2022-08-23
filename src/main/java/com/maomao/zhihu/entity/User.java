package com.maomao.zhihu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 类型 0-管理员 1-普通用户
     */
    private Boolean type;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 创建时间
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

    //关注
    @TableField(exist = false)
    private List<User> follows;

    //文章
    @TableField(exist = false)
    private List<Passage> passage;

    //问题
    @TableField(exist = false)
    private List<Question> question;

    //说说说
    @TableField(exist = false)
    private List<Talk> talk;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}