package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86155
* @description 针对表【answer(回答)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Answer
*/
public interface AnswerMapper extends BaseMapper<Answer> {

    //根据id 查询回答
    List<Answer> getAnswerById(@Param("id")Long id);

    //根据questionId 查询回答
    List<Answer> getAnswerByQuestionId(@Param("id")Long id);

    //根据answerId删除回答
    boolean deleteAnswer(Long answerId);

    //根据answerId查询回答（单个）
    List<Answer> getAnswerByAnswerId(@Param("answer_id")Long answerId);

}




