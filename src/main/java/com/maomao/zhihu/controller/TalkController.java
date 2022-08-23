package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.TalkService;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maomao
 * 2022/8/22 13:05
 */
@Controller
public class TalkController {
    @Resource
    UserService userService;

    @Resource
    TalkService talkService;

    //获取用户的所有说说
    @GetMapping("/talk/manage")
    public String answerManager(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userinfo = userService.getUserinfoById(id);
        List<Talk> talks = userinfo.getTalk();
        model.addAttribute("talks",talks);
        return "talk_manage";
    }

    //删除说说
    @GetMapping("/talk/delete/{talkId}")
    public String deleteTalk(@PathVariable("talkId")Long talkId){
        talkService.deleteTalk(talkId);
        return "redirect:/talk/manage";
    }

    //写说说
    @GetMapping("/talk/write")
    public String talkWrite(){
        return "edit_talk";
    }

    //新增说说
    @PostMapping("/talk/add")
    public String createTalk(Talk talk, HttpSession session){
        User user = (User)session.getAttribute("user");
        talk.setUser(user);
        talkService.createTalk(talk);
        return "redirect:/talk/manage";
    }
}
