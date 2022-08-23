package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author maomao
 * 2022/8/20 14:12
 */
@Controller
public class CreationController {

    @Resource
    UserService userService;
    @Resource
    QuestionService questionService;

    @GetMapping("/creation")
    public String creationPage(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        //得到用户详细信息
        User userinfo = userService.getUserinfoById(id);
        //回答数
        int answerNum = userinfo.getQuestion().size();
        //文章数
        int passageNum = userinfo.getPassage().size();
        //说说数
        int talkNum = userinfo.getTalk().size();
        //提问数
        int raiseNum = questionService.getUserRaiseQuestion(id).size();
        //获得浏览量
        int views = 0;
        for (Question question : userinfo.getQuestion()) {
            views += question.getAnswers().get(0).getViews();
        }
        for (Passage passage : userinfo.getPassage()) {
            views += passage.getViews();
        }
        model.addAttribute("views", views);
        model.addAttribute("answerNum", answerNum);
        model.addAttribute("passageNum", passageNum);
        model.addAttribute("talkNum", talkNum);
        model.addAttribute("raiseNum", raiseNum);
        return "creation";
    }
}
