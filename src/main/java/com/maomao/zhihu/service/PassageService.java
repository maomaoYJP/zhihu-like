package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Comment;
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

    //搜索所有文章
    List<Passage> searchAllPassage(String keyword);

    //搜索用户所有文章
    List<Passage> searchUserPassage(Long userId,String keyword);

    //根据文章Id 获得文章和属于的用户
    Passage getPassageAndUserByPassageId(Long passageId);

    //根据passageId 删除文章
    Integer deletePassage(Long passageId);

    //创建文章
    boolean createPassage(Passage passage);

    //根据passageId 获得评论
    List<Comment> getCommentByPassageId(Long passageId);

    //创建文章评论，包含评论的对应关系
    Boolean createPassageComment(Long userId, Long passageId,Comment comment);
}
