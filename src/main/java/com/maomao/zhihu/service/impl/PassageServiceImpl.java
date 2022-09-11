package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.mapper.CommentMapper;
import com.maomao.zhihu.mapper.UserMapper;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.mapper.PassageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* @author 86155
* @description 针对表【passage(文章)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class PassageServiceImpl extends ServiceImpl<PassageMapper, Passage>
    implements PassageService{

    @Resource
    PassageMapper passageMapper;
    @Resource
    PassageService passageService;
    @Resource
    CommentService commentService;
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public List<Passage> getAllPassage() {
        return passageMapper.getManyPassage();
    }

    @Override
    public Passage getPassageAndUserByPassageId(Long passageId) {
        Passage passage = passageMapper.getPassageAndUserByPassageId(passageId);
        passage.setComments(commentService.getCommentsByPassageId(passageId));
        return passage;
    }

    @Override
    @Transactional
    public Integer deletePassage(Long passageId) {
        passageMapper.deletePassageUser(passageId);
        return passageMapper.deleteById(passageId);
    }

    @Override
    public boolean createPassage(Passage passage) {
        passageService.save(passage);
        return passageMapper.createPassageUserMap(passage.getId(),passage.getUser().getId());
    }

    @Override
    public List<Comment> getCommentByPassageId(Long passageId) {
        return commentService.getCommentsByPassageId(passageId);
    }

    @Override
    @Transactional
    public Boolean createPassageComment(Long userId, Long passageId, Comment comment) {
        //判断评论是单独评论还是回复
        if(comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createPassageCommentMap(passageId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }else{
            //保存评论
            commentService.save(comment);
            //创建评论对应文章关系
            commentMapper.createPassageCommentMap(passageId, comment.getId());
            //创建评论user对应关系
            return userMapper.createUserCommentMap(userId, comment.getId());
        }
    }
}




