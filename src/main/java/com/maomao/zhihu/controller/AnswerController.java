package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.*;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.CommentTipService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
import com.maomao.zhihu.utils.HTMLFilter;
import com.maomao.zhihu.utils.MailSend;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maomao
 * 2022/8/21 12:41
 */

@Controller
public class AnswerController {

    @Resource
    UserService userService;

    @Resource
    AnswerService answerService;

    @Resource
    QuestionService questionService;

    @Resource
    CommentTipService commentTipService;

    @GetMapping("/answer/manage")
    public String answerManager(HttpSession session, Model model) {
        //用户未登录
        if (session.getAttribute("user") == null) {
            return "personal";
        }
        User user = (User) session.getAttribute("user");
        Long id = user.getId();
        User userinfo = userService.getUserinfoById(id);
        List<Question> questions = userinfo.getQuestion();
        sortList.sortQuestion(questions);
        for (Question question : questions) {
            question.getAnswers().get(0).setContent(HTMLFilter.delHTMLTag(question.getAnswers().get(0).getContent()));
        }
        model.addAttribute("questions", questions);
        return "answer_manage";
    }

    //修改回答页面
    @GetMapping("/answer/update/{questionId}/{answerId}")
    public String updatePassage(@PathVariable("questionId")Long questionId,@PathVariable("answerId")Long answerId, Model model){
        Answer answer = answerService.getById(answerId);
        answer.setContent(answer.getContent().replaceAll("'","\\\\'").replaceAll("\"", "\\\\\""));
        Question question = questionService.getById(questionId);
        model.addAttribute("answer", answer);
        model.addAttribute("question", question);
        return "update_answer";
    }

    @PostMapping("/answer/update")
    @ResponseBody
    public String updatePassage(Answer answer, Model model){
        if(answerService.updateById(answer)){
            return "success";
        }else{
            return "error";
        }
    }

    //删除回答
    @GetMapping("/answer/delete/{answerId}")
    public String deleteAnswer(@PathVariable("answerId")Long answerId){
        answerService.deleteAnswer(answerId);
        return "redirect:/answer/manage";
    }

    //回答详情
    @GetMapping("/answer/info/{answerId}")
    public String answerInfo(@PathVariable("answerId") Long answerId, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return "login";
        }
        Question question = questionService.getQuestionByAnswerId(answerId);
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        List<User> follows = userService.getFollowsById(id);
        boolean isFollow = follows.contains(question.getAnswers().get(0).getUser());
        int count = questionService.getQuestionCountByAnswerId(answerId);

        model.addAttribute("count", count);
        model.addAttribute("question", question);
        model.addAttribute("isFollow", isFollow);
        return "answer_info";
    }

    //保存回答评论
    @PostMapping("/answer/{answerId}/comment")
    public String passageComment(@PathVariable("answerId")Long answerId, Comment comment, HttpSession session, Model model){
        //获取用户id
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();

        boolean noTipCondition;
        //不提示情况
        //评论：1. 评论自己的回答
        //回复：1. 回复自己

        //不是回复
        if(comment.getParentComment() == null){
            noTipCondition = userId.equals(comment.getUser().getId()) && comment.getParentCommentId() == -1;
        //回复
        }else{
            noTipCondition =
                    (userId.equals(comment.getUser().getId()) && comment.getParentCommentId() == -1) ||
                            (comment.getParentComment().getUser().getId().equals(userId));
        }
        //创建保存评论
        answerService.createAnswerComment(userId,answerId,comment);

        //如果是自己的评论，就不提示
        if(!noTipCondition){
            //插入comment_tip
            CommentTip commentTip = new CommentTip();
            commentTip.setCommentId(comment.getId());
            commentTip.setPassageId(null);
            commentTip.setAnswerId(answerId);
            commentTip.setUserId(comment.getUser().getId());
            commentTip.setIsRead(0L);
            commentTipService.save(commentTip);

            User receive = userService.getById(comment.getUser().getId());
            //开启新线程，邮箱提示
            MailSend mailSend = new MailSend(receive.getEmail(), comment.getContent());
            mailSend.start();
        }


        //查询文章
        Question question = questionService.getQuestionByAnswerId(answerId);
        model.addAttribute("question", question);
        return "answer_info :: comment-container";
    }

    //跳转编写回答界面
    @GetMapping("/answer/edit/{questionId}")
    public String answerEdit(@PathVariable("questionId") Long questionId,Model model){
        Question question = questionService.getQuestionByQuestionId(questionId);
        model.addAttribute("question", question);
        return "edit_answer";
    }

    //创建回答
    @PostMapping("/answer/add")
    @ResponseBody
    public String answerAdd(String content,Long questionId,String picture, HttpSession session){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "redirect:/login";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();

        //保存回答，及建立表对应关系
        boolean answer = answerService.createAnswer(content, questionId, picture ,id);
        return String.valueOf(questionId);
    }
}
