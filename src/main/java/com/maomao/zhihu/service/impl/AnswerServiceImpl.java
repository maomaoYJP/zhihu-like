package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.mapper.PassageMapper;
import com.maomao.zhihu.mapper.QuestionMapper;
import com.maomao.zhihu.mapper.UserMapper;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.mapper.AnswerMapper;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
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
    @Resource
    QuestionMapper questionMapper;
    @Resource
    UserMapper userMapper;

    @Override
    @Transactional
    public boolean deleteAnswer(Long answerId) {
        answerMapper.deleteAnswer(answerId);
        return answerService.removeById(answerId);
    }

    @Override
    @Transactional
    public boolean createAnswer(String content, Long questionId, Long userId) {
        //创建回答
        Answer answer = new Answer();
        answer.setContent(content);
        //自动将answer中 id 和 content注入
        boolean save = answerService.save(answer);

        //创建问题与回答对应关系
        boolean questionAnswerMap = questionMapper.createQuestionAnswerMap(questionId, answer.getId());

        //创建用户和回答对应关系
        boolean userAnswerMap = userMapper.createUserAnswerMap(answer.getId(), userId);

        return userAnswerMap;
    }
}




