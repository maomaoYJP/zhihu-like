package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.*;
import com.maomao.zhihu.service.CommentTipService;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.UserService;
import com.maomao.zhihu.utils.HTMLFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maomao
 * 2022/8/21 12:58
 */

@Controller
public class PassageController {
    @Resource
    UserService userService;

    @Resource
    PassageService passageService;

    @Resource
    CommentTipService commentTipService;

    @GetMapping("/passage/manage")
    public String answerManager(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "personal";
        }
        User user = (User)session.getAttribute("user");
        Long id = user.getId();
        User userinfo = userService.getUserinfoById(id);
        List<Passage> passages = userinfo.getPassage();
        for (Passage passage : passages) {
            passage.setContent(HTMLFilter.delHTMLTag(passage.getContent()));
        }
        sortList.sortPassage(passages);
        model.addAttribute("passages",passages);
        return "passage_manage";
    }

    @GetMapping("/passage/update/{passageId}")
    public String updatePassage(@PathVariable("passageId")Long passageId, Model model){
        Passage passage = passageService.getById(passageId);
        passage.setContent(passage.getContent().replaceAll("'","\\\\'").replaceAll("\"", "\\\\\""));
        model.addAttribute("passage", passage);
        return "update_passage";
    }

    @PostMapping("/passage/update")
    @ResponseBody
    public String updatePassage(Passage passage, Model model){
        if(passageService.updateById(passage)){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/passage/delete/{passageId}")
    public String deleteAnswer(@PathVariable("passageId")Long passageId){
        Integer passage = passageService.deletePassage(passageId);
        return "redirect:/passage/manage";
    }

    @GetMapping("/passage/write")
    public String passagePage(){
        return "edit_passage";
    }

    //创建passage
    //使用ajax
    @PostMapping("/passage/add")
    @ResponseBody
    public String passageAdd(Passage passage, HttpSession session){
        User user = (User)session.getAttribute("user");
        passage.setUser(user);
        passageService.createPassage(passage);
        return "success";
    }
    
    //文章详情
    @GetMapping("/passage/info/{passageId}")
    public String passageInfo(@PathVariable("passageId")Long passageId, Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return "login";
        }
        User user = (User)session.getAttribute("user");
        Long loginId = user.getId();
        //得到登录者的关注
        List<User> follows = userService.getFollowsById(loginId);
        Passage passage = passageService.getPassageAndUserByPassageId(passageId);

        //判断登录者是否关注了文章作者
        boolean isFollow = follows.contains(passage.getUser());
        model.addAttribute("passage", passage);
        model.addAttribute("isFollow", isFollow);
        return "passage_info";
    }

    //文章评论
    @PostMapping("/passage/{passageId}/comment")
    public String passageComment(@PathVariable("passageId")Long passageId, Comment comment,HttpSession session, Model model){
        //获取用户id
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        boolean noTipCondition;
        //不是回复
        if(comment.getParentComment() == null){
            noTipCondition = userId.equals(comment.getUser().getId()) && comment.getParentCommentId() == -1;
            //回复
        }else{
            noTipCondition =
                    (userId.equals(comment.getUser().getId()) && comment.getParentCommentId() == -1) ||
                            (comment.getParentComment().getUser().getId().equals(userId));
        }

        //创建保存评论
        passageService.createPassageComment(userId, passageId, comment);

        CommentTip commentTip = new CommentTip();
        //如果是自己的评论就不提示,但是如果是在自己的文章回复别人需要提示

        if(!noTipCondition){
            //插入comment_tip
            commentTip.setCommentId(comment.getId());
            commentTip.setPassageId(passageId);
            commentTip.setAnswerId(null);
            commentTip.setUserId(comment.getUser().getId());
            commentTip.setIsRead(0L);
            commentTipService.save(commentTip);
        }

        //查询文章
        Passage passage = passageService.getPassageAndUserByPassageId(passageId);
        model.addAttribute("passage", passage);
        return "passage_info :: comment-container";
    }
}
