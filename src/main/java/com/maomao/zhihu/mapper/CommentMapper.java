package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86155
* @description 针对表【comment(评论)】的数据库操作Mapper
* @createDate 2022-09-08 15:03:24
* @Entity com.maomao.zhihu.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    //根据passageId得到顶级评论，按时间排序
    List<Comment> getTopCommentByPassageIdOrderByTime(Long passageId);

    //根据answerId得到顶级评论，按时间排序
    List<Comment> getTopCommentByAnswerIdOrderByTime(Long answerId);

    //根据answerId得到顶级评论，按时间排序
    List<Comment> getTopCommentByTalkIdOrderByTime(Long talkId);

    //根据parentCommentId 得到该评论的父评论
    Comment getParentCommentByParentCommentId(@Param("parent_comment_id")Long parentCommentId);

    //根据commentId 获取其直接的子评论
    List<Comment> getDirectReplyByCommentId(@Param("id")Long commentId);

    //创建comment 和 passage对应关系
    boolean createPassageCommentMap(Long passageId, Long commentId);

    //创建comment 和 answer对应关系
    boolean createAnswerCommentMap(Long answerId, Long commentId);

    //创建comment 和 talk对应关系
    boolean createTalkCommentMap(Long talkId, Long commentId);
}




