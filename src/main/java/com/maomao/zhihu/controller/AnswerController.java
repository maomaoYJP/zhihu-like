package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("questions",questions);
        return "answer_manage";
    }

    @GetMapping("/answer/delete/{answerId}")
    public String deleteAnswer(@PathVariable("answerId")Long answerId){
        answerService.deleteAnswer(answerId);
        return "redirect:/answer/manage";
    }
}
