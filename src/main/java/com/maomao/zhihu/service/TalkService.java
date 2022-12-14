package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【talk(说说)】的数据库操作Service
* @createDate 2022-08-12 13:04:07
*/
public interface TalkService extends IService<Talk> {

    //获得所有的说说
    List<Talk> getManyTalk();

    //根据talkId获得单个说说
    Talk getTalkByTalkId(Long talkId);

    //根据talkId 删除说说
    boolean deleteTalk(Long talkId);

    //创建说说 及其用户对应关系
    boolean createTalk(Talk talk);

    //创建说说评论，包括评论的对应关系
    Boolean createTalkComment(Long userId, Long talkId, Comment comment);
}
