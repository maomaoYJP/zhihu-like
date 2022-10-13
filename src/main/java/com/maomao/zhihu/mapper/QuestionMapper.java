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

    //根据用户id 获得问题
    List<Question> getQuestionById(@Param("id")Long id);

    //根据问题id获得问题的回答数量
    int getQuestionCountByAnswerId(@Param("answerId")Long answerId);

    //查询用户提出的问题
    List<Question> getQuestionRaise(@Param("id")Long id);

    //搜索所有问题
    List<Question> searchAllQuestion(@Param("keyword")String keyword);

    //搜索用户自己的问题
    List<Question> searchUserQuestion(@Param("userId")Long userId ,@Param("keyword")String keyword);

    //查询全部问题
    List<Question> getManyQuestion();

    //创建新问题和用户对应关系
    boolean createUserQuestion(Long questionId,Long userId);

    //根据answerId 获得问题极其回答
    Question getQuestionByAnswerId(Long answerId);

    //根据questionId 获得问题和回答
    Question getQuestionByQuestionId(Long questionId);

    //根据questionId 和 answerId 建立对应关系
    boolean createQuestionAnswerMap(Long questionId, Long answerId);

    //根据answerId，获得问题
    Question getSingleQuestionByAnswerId(@Param("answer_id")Long answerId);

}




