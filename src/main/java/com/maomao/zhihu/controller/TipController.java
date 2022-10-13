package com.maomao.zhihu.controller;

import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.CommentTip;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.service.CommentTipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maomao
 * 2022/10/13 17:27
 */
@Controller
public class TipController {

    @Resource
    private CommentTipService commentTipService;

    @GetMapping("/tip")
    public String tipPage(HttpSession session, Model model){
        //用户未登录
        if(session.getAttribute("user") == null){
            return "login";
        }
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();

        List<Comment> commentTips = commentTipService.getMyAnswerTip(userId);
        commentTips.addAll(commentTipService.getMyPassageTip(userId));
        List<Comment> newCommentTips = commentTips.stream().filter((comment -> comment.getIsRead() == 0)).collect(Collectors.toList());
        model.addAttribute("commentTips",newCommentTips);
        model.addAttribute("isRead",0);
        return "tip";
    }

    @GetMapping("/tip/read")
    public String tipRead(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();

        List<Comment> commentTips = commentTipService.getMyAnswerTip(userId);
        commentTips.addAll(commentTipService.getMyPassageTip(userId));
        List<Comment> newCommentTips = commentTips.stream().filter((comment -> comment.getIsRead() == 1)).collect(Collectors.toList());
        model.addAttribute("commentTips",newCommentTips);
        model.addAttribute("isRead",1);
        return "tip";
    }

    @PostMapping("/tip/update")
    @ResponseBody
    public String tipUpdate(Long tipId){
        CommentTip commentTip = new CommentTip();
        commentTip.setId(tipId);
        commentTip.setIsRead(1L);
        if(commentTipService.updateById(commentTip)){
            return "success";
        }else{
            return "fail";
        }
    }
}
