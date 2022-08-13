package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Passage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 86155
* @description 针对表【passage(文章)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Passage
*/
public interface PassageMapper extends BaseMapper<Passage> {

    //获得所有文章卡片
    List<Passage> getAllPassageCard();
}




