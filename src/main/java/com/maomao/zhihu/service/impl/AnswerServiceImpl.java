package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.mapper.QuestionMapper;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.mapper.AnswerMapper;
import com.maomao.zhihu.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
* @author 86155
* @description 针对表【answer(回答)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

    @Resource
    AnswerMapper answerMapper;
    @Resource
    AnswerService answerService;

    @Override
    @Transactional
    public boolean deleteAnswer(Long answerId) {
        answerMapper.deleteAnswer(answerId);
        return answerService.removeById(answerId);
    }
}




