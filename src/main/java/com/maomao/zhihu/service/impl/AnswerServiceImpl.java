package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.CommentTip;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.mapper.*;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
* @author 86155
* @description 针对表【answer(回答)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

    @Resource
    AnswerMapper answerMapper;
    @Resource
    AnswerService answerService;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    CommentService commentService;
    @Resource
    CommentTipService commentTipService;

    @Override
    @Transactional
    public boolean deleteAnswer(Long answerId) {
        //删除answer_user,question_answer
        answerMapper.deleteAnswer(answerId);
        //删除回答
        answerService.removeById(answerId);
        //删除comment_tip
        QueryWrapper<CommentTip> wrapper = new QueryWrapper<>();
        wrapper.eq("answer_id",answerId);
        boolean remove = commentTipService.remove(wrapper);
        return remove;
    }

    @Override
    @Transactional
    public boolean createAnswer(String content, Long questionId,String picture,Long userId) {
        //创建回答
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setPicture(picture);
        //自动将answer中 id 和 content注入
        boolean save = answerService.save(answer);

        //创建问题与回答对应关系
        boolean questionAnswerMap = questionMapper.createQuestionAnswerMap(questionId, answer.getId());

        //创建用户和回答对应关系
        boolean userAnswerMap = userMapper.createUserAnswerMap(answer.getId(), userId);

        return userAnswerMap;
    }

    @Override
    @Transactional
    public boolean createAnswerComment(Long userId, Long answerId, Comment comment) {
        //判断评论是单独评论还是回复
        if(comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createAnswerCommentMap(answerId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }else{
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createAnswerCommentMap(answerId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }
    }
}




