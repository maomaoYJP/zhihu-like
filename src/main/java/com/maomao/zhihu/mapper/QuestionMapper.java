package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86155
* @description 针对表【question(问题)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    //根据id 获得问题
    List<Question> getQuestionById(@Param("id")Long id);

    //查询用户提出的问题
    List<Question> getQuestionRaise(@Param("id")Long id);

    //查询全部问题
    List<Question> getManyQuestion();

    //创建新问题和用户对应关系
    boolean createUserQuestion(Long questionId,Long userId);

}




