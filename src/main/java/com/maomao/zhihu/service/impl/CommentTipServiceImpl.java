package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.CommentTip;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.service.CommentTipService;
import com.maomao.zhihu.mapper.CommentTipMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86155
* @description 针对表【comment_tip】的数据库操作Service实现
* @createDate 2022-10-12 23:07:31
*/
@Service
public class CommentTipServiceImpl extends ServiceImpl<CommentTipMapper, CommentTip>
    implements CommentTipService{

    @Resource
    private CommentTipMapper commentTipMapper;

    @Override
    public Integer getMyAnswerTipCount(Long userId) {
        return commentTipMapper.getMyAnswerTipCount(userId);
    }

    @Override
    public Integer getMyPassageTipCount(Long userId) {
        return commentTipMapper.getMyPassageTipCount(userId);
    }

    @Override
    public List<Comment> getMyAnswerTip(Long userId) {
        return commentTipMapper.getMyAnswerTip(userId);
    }

    @Override
    public List<Comment> getMyPassageTip(Long userId) {
        return commentTipMapper.getMyPassageTip(userId);
    }
}




