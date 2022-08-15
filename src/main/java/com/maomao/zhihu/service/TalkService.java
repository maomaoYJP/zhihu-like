package com.maomao.zhihu.service;

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
}
