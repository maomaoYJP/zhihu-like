package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.mapper.CommentMapper;
import com.maomao.zhihu.mapper.UserMapper;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.service.TalkService;
import com.maomao.zhihu.mapper.TalkMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86155
* @description 针对表【talk(说说)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk>
    implements TalkService{

    @Resource
    TalkMapper talkMapper;
    @Resource
    TalkService talkService;
    @Resource
    CommentService commentService;
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public List<Talk> getManyTalk() {
        List<Talk> talks = talkMapper.getManyTalk();
        for (Talk talk : talks) {
            talk.setComments(commentService.getCommentByTalkId(talk.getId()));
        }
        return talks;
    }

    @Override
    public Talk getTalkByTalkId(Long talkId) {
        return talkMapper.getTalkByTalkId(talkId);
    }

    @Override
    public boolean deleteTalk(Long talkId) {
        talkMapper.deleteTalkUserMap(talkId);
        return talkService.removeById(talkId);
    }

    @Override
    public boolean createTalk(Talk talk) {
        talkService.save(talk);
        return talkMapper.createTalkUserMap(talk.getId(), talk.getUser().getId());
    }

    @Override
    @Transactional
    public Boolean createTalkComment(Long userId, Long talkId, Comment comment) {
        //判断评论是单独评论还是回复
        if(comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createTalkCommentMap(talkId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }else{
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createTalkCommentMap(talkId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }
    }
}




