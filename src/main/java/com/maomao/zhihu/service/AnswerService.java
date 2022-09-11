package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.queryvo.QuestionAnswer;

import java.util.List;

/**
* @author 86155
* @description 针对表【answer(回答)】的数据库操作Service
* @createDate 2022-08-12 13:04:07
*/
public interface AnswerService extends IService<Answer> {

    //根据answerId 删除回答
    boolean deleteAnswer(Long answerId);

    //创建回答
    boolean createAnswer(String content,Long questionId, Long UserId);

    //创建回答评论
    boolean createAnswerComment(Long userId, Long answerId, Comment comment);
}
