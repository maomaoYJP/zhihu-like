package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86155
* @description 针对表【talk(说说)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Talk
*/
public interface TalkMapper extends BaseMapper<Talk> {

    //获得所有用户发布的说说
    List<Talk> getAllTalk();
}




