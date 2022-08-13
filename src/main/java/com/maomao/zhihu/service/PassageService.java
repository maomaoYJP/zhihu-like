package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Passage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【passage(文章)】的数据库操作Service
* @createDate 2022-08-12 13:04:07
*/
public interface PassageService extends IService<Passage> {

    //获得所有文章卡片
    List<Passage> getAllPassageCard();
}
