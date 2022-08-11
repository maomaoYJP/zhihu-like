package com.maomao.blog.controller;

import com.maomao.blog.exception.PageNotFindException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author maomao
 * 2022/8/11 11:23
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
