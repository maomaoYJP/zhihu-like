package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【question(问题)】的数据库操作Service
* @createDate 2022-08-12 13:04:07
*/
public interface QuestionService extends IService<Question> {

    //获得所有问题
    List<Question> getManyQuestion();

    //根据关键词搜索问题
    List<Question> searchAllQuestion(String keyword);

    //根据关键词搜索某个用户问题
    List<Question> searchUserQuestion(Long userId,String keyword);

    //查询问题排行
    List<Question> getQuestionRank();

    //查询用户提出的问题
    List<Question> getUserRaiseQuestion(Long userId);

    //根据answerId获得问题的回答数
    int getQuestionCountByAnswerId(Long answerId);

    //创建新问题
    boolean createQuestion(Question question, Long userId);

    //修改问题
    boolean updateQuestion(Question question);

    //根据回答Id，查询问题(附带回答)
    Question getQuestionByAnswerId(Long answerId);

    //根据问题Id，查询问题及回答
    Question getQuestionByQuestionId(Long questionId);

    //删除问题和user对应关系
    //删除问题和answer对应关系
    boolean deleteQuestionMap(Long questionId);


}
