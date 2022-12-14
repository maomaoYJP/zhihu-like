package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Passage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86155
* @description 针对表【passage(文章)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Passage
*/
public interface PassageMapper extends BaseMapper<Passage> {

    //根据id获得文章
    List<Passage> getPassageById(@Param("id")Long id);

    //根据passageId获取单个文章
    Passage getSinglePassageByPassageId(@Param("passage_id")Long passageId);

    //获得所有文章
    List<Passage> getManyPassage();

    //搜索所有的文章
    List<Passage> searchAllPassage(@Param("keyword")String keyword);

    //所有用户所有的文章
    List<Passage> searchUserPassage(@Param("userId")Long userId, @Param("keyword")String keyword);

    //根据passageId 删除文章与user对应关系
    boolean deletePassageUser(Long passageId);

    //根据passageId 和 userId 增加passage和用户的对应关系
    boolean createPassageUserMap(Long passageId, Long userId);

    //根据passageId 获得文章和用户
    Passage getPassageAndUserByPassageId(Long passageId);

}




