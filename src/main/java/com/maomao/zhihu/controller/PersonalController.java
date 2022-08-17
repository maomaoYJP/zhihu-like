package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maomao
 * 2022/8/17 10:02
 */
@Controller
public class PersonalController {

    @Resource
    UserService userService;

    @RequestMapping("/followList")
    public String followList(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        List<User> follows = userService.getFollowsById(id);
        model.addAttribute("follows",follows);
        return "follow_list";
    }

    @RequestMapping("/beFollowedList")
    public String beFollowedList(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        List<User> follows = userService.getBeFollowedById(id);
        model.addAttribute("follows",follows);
        return "follow_list";
    }

    @PostMapping("/addFollow")
    @ResponseBody
    public String addFollow(HttpSession session,Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.addFollowById(userId,followId);
        return "取关";
    }
    @PostMapping("/removeFollow")
    @ResponseBody
    public String removeFollow(HttpSession session,Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.removeFollowById(userId,followId);
        return "关注";
    }
}
