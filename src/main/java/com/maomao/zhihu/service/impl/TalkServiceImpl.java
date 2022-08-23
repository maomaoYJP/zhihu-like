package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.service.TalkService;
import com.maomao.zhihu.mapper.TalkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86155
* @description 针对表【talk(说说)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk>
    implements TalkService{

    @Resource
    TalkMapper talkMapper;
    @Resource
    TalkService talkService;

    @Override
    public List<Talk> getManyTalk() {
        return talkMapper.getManyTalk();
    }

    @Override
    public boolean deleteTalk(Long talkId) {
        talkMapper.deleteTalkUserMap(talkId);
        return talkService.removeById(talkId);
    }

    @Override
    public boolean createTalk(Talk talk) {
        talkService.save(talk);
        return talkMapper.createTalkUserMap(talk.getId(), talk.getUser().getId());
    }
}




