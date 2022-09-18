package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.utils.HTMLFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author maomao
 * 2022/9/17 21:35
 */
@Controller
public class SearchController {

    @Resource
    QuestionService questionService;
    @Resource
    PassageService passageService;

    @GetMapping("/search")
    public String search(@Param("type")String type,@Param("keyword")String keyword, Model model){
        if(type.equals("question")){
            List<Question> questions = questionService.searchAllQuestion(keyword);
            for (Question question : questions) {
                if(question.getAnswers().size() != 0){
                    question.getAnswers().get(0).setContent(HTMLFilter.delHTMLTag(question.getAnswers().get(0).getContent()));
                }
            }
            model.addAttribute("questions", questions);
        }

        if(type.equals("passage")){
            List<Passage> passages = passageService.searchAllPassage(keyword);
            for (Passage passage : passages) {
                passage.setContent(HTMLFilter.delHTMLTag(passage.getContent()));
            }
            model.addAttribute("passages",passages);
        }

        model.addAttribute("keyword",keyword);
        model.addAttribute("type",type);
        return "search_page";
    }

    @PostMapping("/search")
    public String searchPage(@Param("keyword") String keyword,@Param("type")String type, Model model){
        model.addAttribute("keyword",keyword);
        model.addAttribute("type",type);
        if(type.equals("question")){
            List<Question> questions = questionService.searchAllQuestion(keyword);
            for (Question question : questions) {
                if(question.getAnswers().size() != 0){
                    question.getAnswers().get(0).setContent(HTMLFilter.delHTMLTag(question.getAnswers().get(0).getContent()));
                }
            }
            model.addAttribute("questions", questions);
            return "search_page :: search";
        }

        if(type.equals("passage")){
            List<Passage> passages = passageService.searchAllPassage(keyword);
            for (Passage passage : passages) {
                passage.setContent(HTMLFilter.delHTMLTag(passage.getContent()));
            }
            model.addAttribute("passages",passages);
            return "search_page :: search";
        }
        return "error/404";
    }
}
