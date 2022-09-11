package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.mapper.CommentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86155
* @description 针对表【comment(评论)】的数据库操作Service实现
* @createDate 2022-09-08 15:03:24
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByPassageId(Long passageId) {
        List<Comment> comments = commentMapper.getTopCommentByPassageIdOrderByTime(passageId);
        return getAllCommentAndTopCommentReplyToList(comments);
    }

    @Override
    public List<Comment> getCommentByAnswerId(Long answerId) {
        List<Comment> comments = commentMapper.getTopCommentByAnswerIdOrderByTime(answerId);
        return getAllCommentAndTopCommentReplyToList(comments);
    }

    @Override
    public List<Comment> getCommentByTalkId(Long talkId) {
        List<Comment> comments = commentMapper.getTopCommentByTalkIdOrderByTime(talkId);
        return getAllCommentAndTopCommentReplyToList(comments);
    }

    /**
     * 获取所有的顶级评论，并且获取其所有回复
     * @param comments
     * @return
     */
    private List<Comment> getAllCommentAndTopCommentReplyToList(List<Comment> comments){
        //重新复制一份comments,避免出现对于数据库的误操作
        List<Comment> copyComments = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            copyComments.add(c);
        }
        //合并所有评论到顶级评论
        collectChildren(copyComments);
        setReplyCommentNULL(copyComments);
        return copyComments;
    }

    /**
     * 将replyComment都设为null，减少不必要空间使用
     */
    private void setReplyCommentNULL(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment replyComment : replyComments) {
                replyComment.setReplyComments(null);
            }
        }
    }

    /**
     * 合并所有评论到顶级评论
     * @param comments
     */
    private void collectChildren(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment replyComment : replyComments) {
                recursiveReply(replyComment);
            }
            //迭代后获得所有子集评论，放入顶级评论中
            comment.setReplyComments(tempReply);
            //清除临时集合
            tempReply = new ArrayList<>();
        }
    }

    //临时集合，用于储存迭代得到的所有子集评论
    private List<Comment> tempReply = new ArrayList<>();

    /**
     * 迭代，得到所有的子集评论
     * @param reply
     */
    private void recursiveReply(Comment reply){
        //第一个评论
        tempReply.add(reply);
        if(reply.getReplyComments().size() > 0){
            List<Comment> replyComments = reply.getReplyComments();
            for (Comment replyComment : replyComments) {
                if(replyComment.getReplyComments().size()>0){
                    recursiveReply(replyComment);
                }else{
                    tempReply.add(replyComment);
                }
            }
        }
    }
}




