package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
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

    @GetMapping("/answer/manage")
    public String answerManager(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userinfo = userService.getUserinfoById(id);
        List<Question> questions = userinfo.getQuestion();
        sortList.sortQuestion(questions);
        model.addAttribute("questions",questions);
        return "answer_manage";
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
        //创建保存评论
        answerService.createAnswerComment(userId,answerId,comment);

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
