package com.maomao.zhihu.queryvo;

import com.maomao.zhihu.entity.Answer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author maomao
 * 2022/8/12 12:47
 */

@Data
public class QuestionAnswer{

    private Long id;

    /**
     * 问题
     */
    private String title;

    /**
     * 问题介绍
     */
    private String introduce;

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
    private Boolean isDelete;

    private List<Answer> answers;
}
