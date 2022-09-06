package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maomao
 * 2022/8/21 12:58
 */

@Controller
public class PassageController {
    @Resource
    UserService userService;

    @Resource
    PassageService passageService;

    @GetMapping("/passage/manage")
    public String answerManager(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userinfo = userService.getUserinfoById(id);
        List<Passage> passages = userinfo.getPassage();
        model.addAttribute("passages",passages);
        return "passage_manage";
    }

    @GetMapping("/passage/delete/{passageId}")
    public String deleteAnswer(@PathVariable("passageId")Long passageId){
        Integer passage = passageService.deletePassage(passageId);
        return "redirect:/passage/manage";
    }

    @GetMapping("/passage/write")
    public String passagePage(){
        return "edit_passage";
    }

    //创建passage
    //使用ajax
    @PostMapping("/passage/add")
    @ResponseBody
    public String passageAdd(Passage passage, HttpSession session){
        User user = (User)session.getAttribute("user");
        passage.setUser(user);
        passageService.createPassage(passage);
        return "success";
    }
    
    //文章详情
    @GetMapping("/passage/info/{passageId}")
    public String passageInfo(@PathVariable("passageId")Long passageId, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return "login";
        }
        User user = (User)session.getAttribute("user");
        Long loginId = user.getId();
        //得到登录者的关注
        List<User> follows = userService.getFollowsById(loginId);
        Passage passage = passageService.getPassageAndUserByPassageId(passageId);

        //判断登录者是否关注了文章作者
        boolean isFollow = follows.contains(passage.getUser());
        model.addAttribute("passage", passage);
        model.addAttribute("isFollow", isFollow);
        return "passage_info";
    }
}
