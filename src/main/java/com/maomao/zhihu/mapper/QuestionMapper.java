package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maomao.zhihu.queryvo.QuestionAnswer;

import java.util.List;

/**
* @author 86155
* @description 针对表【question(问题)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> getManyQuestionAnswer();
}




