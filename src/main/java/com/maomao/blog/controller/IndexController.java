package com.maomao.blog.controller;

import com.maomao.blog.entity.Answer;
import com.maomao.blog.entity.Passage;
import com.maomao.blog.exception.PageNotFindException;
import com.maomao.blog.service.AnswerService;
import com.maomao.blog.service.PassageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maomao
 * 2022/8/11 11:23
 */

@Controller
public class IndexController {

    @Resource
    AnswerService answerService;

    @Resource
    PassageService passageService;

    @RequestMapping("/")
    public String index(Model model){
        List<Answer> manyAnswer = answerService.getManyAnswer();
        List<Passage> manyPassage = passageService.getManyPassage();
        List<Object> answerAndPassage = new ArrayList<>();
        answerAndPassage.add(manyAnswer);
        answerAndPassage.add(manyPassage);
        model.addAttribute("answerAndPassage",answerAndPassage);
        return "index";
    }
}
