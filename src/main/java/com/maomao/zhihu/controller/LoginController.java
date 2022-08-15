package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String login(User user, HttpSession session){
        User user1 = userService.checkUser(user.getUsername(), user.getPassword());
        if(user1 != null){
            //密码置空，防止泄露
            user1.setPassword(null);
            session.setAttribute("user",user1);
            return "redirect:/";
        }else{
            return "login";
        }
    }
}
