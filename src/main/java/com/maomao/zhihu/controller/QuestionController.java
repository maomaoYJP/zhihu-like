package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.utils.HTMLFilter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maomao
 * 2022/8/22 14:50
 */

@Controller
public class QuestionController {
    @Resource
    QuestionService questionService;
    @Resource
    AnswerService answerService;

    @GetMapping("/question/manage")
    public String questionManage(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        List<Question> questions = questionService.getUserRaiseQuestion(id);
        model.addAttribute("questions", questions);
        return "raise_question";
    }

    //提出新问题
    @PostMapping("/question/raise")
    public String questionRaise(Question question, HttpSession session){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        questionService.createQuestion(question,id);
        return "redirect:/question/manage";
    }

    //修改问题
    @PostMapping("/question/edit")
    public String questionEdit(Question question){
        questionService.updateQuestion(question);
        return "redirect:/question/manage";
    }

    //全部问题
    @GetMapping("/question/all")
    public String allQuestion(Model model){
        List<Question> questions = questionService.getManyQuestion();
        model.addAttribute("questions", questions);
        return "question";
    }

    //问题回答列表
    @GetMapping("/question/questionAnswer/{questionId}")
    public String questionAnswer(@PathVariable("questionId")Long questionId, Model model){
        Question question = questionService.getQuestionByQuestionId(questionId);
        for (Answer answer : question.getAnswers()) {
            answer.setContent(HTMLFilter.delHTMLTag(answer.getContent()));
        }
        model.addAttribute("question", question);
        return "answer_list";
    }

    //删除问题
    @GetMapping("/question/delete/{questionId}")
    @Transactional
    public String questionDelete(@PathVariable("questionId")Long questionId){
        //删除问题
        boolean b = questionService.removeById(questionId);
        //批量删除问题中的回答,即对应关系
        List<Long> answerId = new ArrayList<>();
        for (Answer answer : questionService.getQuestionByQuestionId(questionId).getAnswers()) {
            answerId.add(answer.getId());
        }

        for (Long id : answerId) {
            answerService.deleteAnswer(id);
        }
        //删除对应关系
        questionService.deleteQuestionMap(questionId);

        return "redirect:/question/manage";
    }
}
