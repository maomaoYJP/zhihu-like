package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.mapper.PassageMapper;
import com.maomao.zhihu.mapper.QuestionMapper;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.TalkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
    TalkService talkService;
    @Resource
    PassageMapper passageMapper;

    //首页
    //回答页
    @RequestMapping("/")
    public String index(Model model){
        List<Question> questionAnswer = answerService.getAllAnswerCard();
        model.addAttribute("questionAnswer", questionAnswer);
        return "index";
    }

    //首页文章页
    @RequestMapping("/passage")
    public String indexPassage(Model model){
        List<Passage> passageList = passageMapper.getAllPassageCard();
        model.addAttribute("passageList",passageList);
        return "index_passage";
    }

    //说说页
    @RequestMapping("/talk")
    public String allTalk(Model model){
        List<Talk> talkList = talkService.getAllTalk();
        model.addAttribute("talkList", talkList);
        return "talk";
    }

    //热榜
    @RequestMapping("/rank")
    public String getRank(Model model){
        List<Question> questionRank = answerService.getQuestionRank();
        model.addAttribute("questionRank",questionRank);
        return "recommend";
    }
}