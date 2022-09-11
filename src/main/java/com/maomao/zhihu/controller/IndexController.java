package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.*;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.TalkService;
import com.maomao.zhihu.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author maomao
 * 2022/8/11 11:23
 */

@Controller
public class IndexController {

    @Resource
    QuestionService questionService;
    @Resource
    TalkService talkService;
    @Resource
    PassageService passageService;
    @Resource
    UserService userService;

    //首页
    //回答页
    @RequestMapping("/")
    public String index(Model model){
        List<Question> questions = questionService.getManyQuestion();
        model.addAttribute("questions", questions);
        return "index";
    }

    //首页文章页
    @RequestMapping("/passage")
    public String indexPassage(Model model){
        List<Passage> passageList = passageService.getAllPassage();
        model.addAttribute("passageList",passageList);
        return "index_passage";
    }

    //说说页
    @RequestMapping("/talk")
    public String allTalk(Model model){
        List<Talk> talks = talkService.getManyTalk();
        model.addAttribute("talks", talks);
        return "talk";
    }

    //热榜
    @RequestMapping("/rank")
    public String getRank(Model model){
        List<Question> questionRank = questionService.getQuestionRank();
        model.addAttribute("questionRank",questionRank);
        return "recommend";
    }

    //首页关注页
    @RequestMapping("/follow/**")
    public String followPage(HttpSession session, Model model, HttpServletRequest request){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "follow";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        //通过用户的id获得用户的关注
        List<User> follows = userService.getFollowsById(id);
        //初始化
        List<Question> question = new ArrayList<>();
        List<Passage> passage = new ArrayList<>();
        List<Talk> talk = new ArrayList<>();

        //遍历关注列表，获得问题，文章，说说
        for (User follow : follows) {
            Long followId = follow.getId();

            User detailUser = userService.getUserinfoById(followId);
            question.addAll(detailUser.getQuestion());
            passage.addAll(detailUser.getPassage());
            talk.addAll(detailUser.getTalk());
        }
        //按时间先后排序
        sortList.sortQuestion(question);
        sortList.sortPassage(passage);
        sortList.sortTalk(talk);

        String[] split = request.getRequestURL().toString().split("/");
        String requestURL = split[split.length - 1];
        if(requestURL.equals("answer")){
            model.addAttribute("questions",question);
            return "follow :: answerCart";
        }else if(requestURL.equals("passage")){
            model.addAttribute("passages",passage);
            return "follow :: passageCart";
        }else if(requestURL.equals("talk")){
            model.addAttribute("talks",talk);
            return "follow :: talkCart";
        }
        model.addAttribute("questions",question);
        return "follow";
    }


    //首页 我的页
    @RequestMapping("/personal")
    public String personalPage(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        //得到用户详细信息
        User userinfo = userService.getUserinfoById(id);
        //创作数
        int createNum = userinfo.getQuestion().size() + userinfo.getPassage().size();
        //关注数
        int followNum = userService.getFollowsById(id).size();
        //浏览数
        int views = 0;
        for (Question question : userinfo.getQuestion()) {
            views += question.getAnswers().get(0).getViews();
        }
        for (Passage passage : userinfo.getPassage()) {
            views += passage.getViews();
        }

        model.addAttribute("createNum", createNum);
        model.addAttribute("followNum", followNum);
        model.addAttribute("views", views);
        return "personal";
    }

}

class sortList{

    public static void sortQuestion(List<Question> questions){
        questions.sort(new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
    }

    public static void sortPassage(List<Passage> passages){
        passages.sort(new Comparator<Passage>() {
            @Override
            public int compare(Passage o1, Passage o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
    }

    public static void sortTalk(List<Talk> talks){
        talks.sort(new Comparator<Talk>() {
            @Override
            public int compare(Talk o1, Talk o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
    }
}