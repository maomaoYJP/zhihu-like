package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【comment(评论)】的数据库操作Service
* @createDate 2022-09-08 15:03:24
*/
public interface CommentService extends IService<Comment> {

    List<Comment> getCommentsByPassageId(Long passageId);

    List<Comment> getCommentByAnswerId(Long answerId);

    List<Comment> getCommentByTalkId(Long talkId);
}
