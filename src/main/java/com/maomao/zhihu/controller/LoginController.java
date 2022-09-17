package com.maomao.zhihu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author maomao
 * 2022/8/13 19:54
 */

@Controller
public class LoginController {

    @Resource
    UserService userService;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(User user, HttpSession session){
        User user1 = userService.checkUser(user.getUsername(), user.getPassword());
        if(user1 != null){
            //密码置空，防止泄露
            user1.setPassword(null);
            session.setAttribute("user",user1);
            return "success";
        }else{
            return "fail";
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(User user){
        if(userService.save(user)){
            return "success";
        }else{
            return "fail";
        }
    }

    @PostMapping("/register/checkNickname")
    @ResponseBody
    public String checkNickname(String nickname){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname);
        User user = userService.getOne(wrapper);
        if(user != null){
            return "success";
        }else{
            return "fail";
        }
    }

    @PostMapping("/register/checkUsername")
    @ResponseBody
    public String checkUsername(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userService.getOne(wrapper);
        if(user != null){
            return "success";
        }else{
            return "fail";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "personal";
    }
}
