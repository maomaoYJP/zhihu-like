package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.CommentTip;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86155
* @description 针对表【comment_tip】的数据库操作Mapper
* @createDate 2022-10-12 23:07:31
* @Entity com.maomao.zhihu.entity.CommentTip
*/
public interface CommentTipMapper extends BaseMapper<CommentTip> {

    /**
     * 根据userId 查询用户的回答未读评论数量
     * @param userId
     * @return
     */
    Integer getMyAnswerTipCount(Long userId);

    /**
     * 根据userId 查询用户的文章未读评论数量
     * @param userId
     * @return
     */
    Integer getMyPassageTipCount(Long userId);

    /**
     * 查询用户回答评论
     * @param userId
     * @return
     */
    List<Comment> getMyAnswerTip(Long userId);

    /**
     * 查询用户文章评论
     * @param userId
     * @return
     */
    List<Comment> getMyPassageTip(Long userId);
}




