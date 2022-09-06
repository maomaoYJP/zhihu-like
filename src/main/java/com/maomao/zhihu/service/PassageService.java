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

    //获得所有文章
    List<Passage> getAllPassage();

    //根据文章Id 获得文章和属于的用户
    Passage getPassageAndUserByPassageId(Long passageId);

    //根据passageId 删除文章
    Integer deletePassage(Long passageId);

    //创建文章
    boolean createPassage(Passage passage);
}
