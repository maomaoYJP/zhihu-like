package com.maomao.zhihu.mapper;


import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author maomao
 * 2022/8/12 18:38
 */
@SpringBootTest
public class TalkMapperTest {
    @Resource
    TalkMapper talkMapper;
    @Resource
    PassageMapper passageMapper;
    @Resource
    CommentService commentService;
    @Resource
    CommentMapper commentMapper;

    @Test
    public void getAllTalk() {
//        List<Talk> allTalk = talkMapper.getAllTalk();
//        allTalk.forEach(System.out::println);
        List<Comment> comments = commentService.getCommentsByPassageId(1L);
        //List<Comment> replyComments = comments.get(comments.size() - 1).getReplyComments();
        //List<Comment> comments = commentMapper.getTopCommentByPassageIdOrderByTime(1L);
        Comment comment = comments.get(comments.size() - 1);
        List<Comment> replyComments = comment.getReplyComments();
        //System.out.println(replyComments.size());
        replyComments.forEach(System.out::println);
        //comments.forEach(System.out::println);
    }
}