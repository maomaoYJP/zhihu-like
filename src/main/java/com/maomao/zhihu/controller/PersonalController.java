package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
import com.maomao.zhihu.utils.HTMLFilter;
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
    @Resource
    CommentService commentService;

    @GetMapping("/home/{userId}")
    public String home(@PathVariable("userId")Long userId, Model model, HttpSession session){
        User loginUser = (User)session.getAttribute("user");
        Long id = loginUser.getId();
        //判断是否是自己的主页
        if(userId.equals(id)){
            model.addAttribute("myHome", true);
        }else{
            model.addAttribute("myHome", false);
        }

        User user = userService.getById(userId);
        List<User> followsById = userService.getFollowsById(userId);
        List<User> beFollowedById = userService.getBeFollowedById(userId);
        //获得粉丝数和关注数
        int followSize = followsById.size();
        int beFollowedSize = beFollowedById.size();

        //判断是否是自己的关注
        List<User> follows = userService.getFollowsById(id);
        int count = 0;
        for (User follow : follows) {
            if(follow.getId().equals(userId)) {
                break;
            }
            count++;
        }
        if(count != follows.size()){
            model.addAttribute("isFollow",true);
        }else{
            model.addAttribute("isFollow",false);
        }

        //获取用户回答、文章、说说
        //通过用户的id获得用户信息
        User userinfo = userService.getUserinfoById(userId);
        //按时间先后排序
        sortList.sortQuestion(userinfo.getQuestion());
        //sortList.sortPassage(userinfo.getPassage());
        //sortList.sortTalk(userinfo.getTalk());

        for (Question question : userinfo.getQuestion()) {
            question.getAnswers().get(0).setContent(HTMLFilter.delHTMLTag(question.getAnswers().get(0).getContent()));
        }
        model.addAttribute("questions",userinfo.getQuestion());
        //model.addAttribute("passages",userinfo.getPassage());
        //model.addAttribute("talks",userinfo.getTalk());
        model.addAttribute("user", user);
        model.addAttribute("followSize", followSize);
        model.addAttribute("beFollowedSize", beFollowedSize);
        return "homepage";
    }

    @GetMapping("/home/{userId}/answer")
    public String homeAnswer(@PathVariable("userId")Long userId, Model model){
        User userinfo = userService.getUserinfoById(userId);
        sortList.sortQuestion(userinfo.getQuestion());
        for (Question question : userinfo.getQuestion()) {
            question.getAnswers().get(0).setContent(HTMLFilter.delHTMLTag(question.getAnswers().get(0).getContent()));
        }
        model.addAttribute("questions", userinfo.getQuestion());
        return "homepage :: answerCart";
    }

    @GetMapping("/home/{userId}/passage")
    public String homePassage(@PathVariable("userId")Long userId, Model model){
        User userinfo = userService.getUserinfoById(userId);
        sortList.sortPassage(userinfo.getPassage());
        for (Passage passage : userinfo.getPassage()) {
            passage.setContent(HTMLFilter.delHTMLTag(passage.getContent()));
        }
        model.addAttribute("passages", userinfo.getPassage());
        return "homepage :: passageCart";
    }

    @GetMapping("/home/{userId}/talk")
    public String homeTalk(@PathVariable("userId")Long userId, Model model){
        User userinfo = userService.getUserinfoById(userId);
        sortList.sortTalk(userinfo.getTalk());
        for (Talk talk : userinfo.getTalk()) {
            talk.setComments(commentService.getCommentByTalkId(talk.getId()));
        }
        model.addAttribute("talks", userinfo.getTalk());
        return "homepage :: talkCart";
    }

    @RequestMapping("/followList/{userId}")
    public String followList(@PathVariable("userId")Long userId, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long loginId = user.getId();
        List<User> follows;
        if(userId.equals(loginId)){
            follows = new ArrayList<>(userService.getFollowsById(loginId));
            model.addAttribute("follows", follows);
            model.addAttribute("userId", userId);
        }else{
            List<User> oldFollows = new ArrayList<>(userService.getFollowsById(loginId));
            List<User> myFollows = new ArrayList<>();
            follows = new ArrayList<>(userService.getFollowsById(userId));
            //别人的主页
            //判断每个关注者，自己是否有关注
            for (User follow : follows) {
                for (User oldFollow : oldFollows) {
                    if(follow.getId().equals(oldFollow.getId())){
                        myFollows.add(follow);
                        oldFollows.remove(oldFollow);
                        break;
                    }
                }
            }
            follows.removeAll(myFollows);
            model.addAttribute("myFollows", myFollows);
            model.addAttribute("follows", follows);
            model.addAttribute("userId", userId);
        }


        return "follow_list";
    }

    @RequestMapping("/beFollowedList/{userId}")
    public String beFollowedList(@PathVariable("userId")Long userId, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long loginId = user.getId();

        List<User> beFollowed;
        List<User> follows;
        if(userId.equals(loginId)){
            //粉丝
            beFollowed = new ArrayList<>(userService.getBeFollowedById(loginId));
            //关注的
            follows = new ArrayList<>(userService.getFollowsById(loginId));
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
        }else{
            //粉丝
            beFollowed = new ArrayList<>(userService.getBeFollowedById(userId));
            //我关注的
            follows = new ArrayList<>(userService.getFollowsById(loginId));
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
        }

        return "beFollowed_list";
    }

    @GetMapping("/addFollow/{followId}")
    @ResponseBody
    public String addFollow(HttpSession session,@PathVariable("followId") Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.addFollowById(userId,followId);
        return "addFollowSuccess";
    }

    @GetMapping("/removeFollow/{followId}")
    @ResponseBody
    public String removeFollow(HttpSession session,@PathVariable("followId") Long followId){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.removeFollowById(userId,followId);
        return "removeFollowSuccess";
    }

    //回答详情界面关注
    @PostMapping("/addFollow")
    public String addFollowPage(Long followId, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.addFollowById(userId,followId);
        model.addAttribute("isFollow", true);
        return "answer_info :: followBtn";
    }

    //回答详情界面取消关注
    @PostMapping("/cancelFollow")
    public String cancelFollowPage(Long followId, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        userService.removeFollowById(userId,followId);
        model.addAttribute("isFollow", false);
        return "answer_info :: followBtn";
    }

    @GetMapping("/editData")
    public String userInfo(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userInfo = userService.getUserinfoById(id);
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("my", true);
        return "user_info";
    }

    @GetMapping("/lookUserInfo")
    public String lookUserInfo(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userInfo = userService.getUserinfoById(id);
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("my", false);
        return "user_info";
    }

    @PostMapping("/editData/save")
    @Transactional
    public String saveUserData(User user){
        userService.saveEditData(user);
        return "redirect:/editData";
    }

}
