package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maomao
 * 2022/8/17 10:02
 */
@Controller
public class PersonalController {

    @Resource
    UserService userService;

    @GetMapping("/home/**")
    public String home(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        List<User> followsById = userService.getFollowsById(id);
        List<User> beFollowedById = userService.getBeFollowedById(id);
        //获得粉丝数和关注数
        int followSize = followsById.size();
        int beFollowedSize = beFollowedById.size();

        //获取用户回答、文章、说说
        //通过用户的id获得用户信息
        User userinfo = userService.getUserinfoById(id);
        //按时间先后排序
        sortList.sortQuestion(userinfo.getQuestion());
        sortList.sortPassage(userinfo.getPassage());
        sortList.sortTalk(userinfo.getTalk());

        model.addAttribute("questions",userinfo.getQuestion());
        model.addAttribute("passages",userinfo.getPassage());
        model.addAttribute("talks",userinfo.getTalk());

        model.addAttribute("followSize", followSize);
        model.addAttribute("beFollowedSize", beFollowedSize);
        return "homepage";
    }

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
        //粉丝
        List<User> beFollowed = userService.getBeFollowedById(id);
        //关注的
        List<User> follows = userService.getFollowsById(id);

        //粉丝又关注排在前面不是关注排在后面
        List<User> followEach = new ArrayList<>();
        for (User user1 : beFollowed) {
            boolean contains = follows.contains(user1);
            if(contains){
                followEach.add(user1);
            }
        }
        for (User each : followEach) {
            boolean contains = beFollowed.contains(each);
            if(contains){
                beFollowed.remove(each);
            }
        }
        int size = followEach.size();
        followEach.addAll(beFollowed);
        model.addAttribute("follows",followEach);
        model.addAttribute("followSize",size);
        return "follow_list";
    }

    @GetMapping("/addFollow/{followId}")
    public String addFollow(HttpSession session,@PathVariable("followId") Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.addFollowById(userId,followId);
        return "redirect:/followList";
    }

    @GetMapping("/removeFollow/{followId}")
    public String removeFollow(HttpSession session,@PathVariable("followId") Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.removeFollowById(userId,followId);
        return "redirect:/followList";
    }

    @GetMapping("/editData")
    public String userInfo(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userInfo = userService.getUserinfoById(id);
        model.addAttribute("userInfo",userInfo);
        return "user_info";
    }

    @PostMapping("/editData/save")
    @Transactional
    public String saveUserData(User user,String birthdayString){
        Date date = Date.valueOf(birthdayString);
        user.setBirthday(date);
        userService.saveEditData(user);
        return "redirect:/editData";
    }

}
