package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
* @author 86155
* @description 针对表【question(问题)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Resource
    QuestionMapper questionMapper;
    @Resource
    QuestionService questionService;

    @Override
    public List<Question> getManyQuestion() {
        return questionMapper.getManyQuestion();
    }

    @Override
    public List<Question> getQuestionRank() {
        List<Question> questionList = questionMapper.getManyQuestion();
        questionList.sort(new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                int view1 = 0,view2 = 0;
                for (Answer answer : o1.getAnswers()) {
                    view1 += answer.getViews();
                }
                for (Answer answer : o2.getAnswers()) {
                    view2 += answer.getViews();
                }
                return view1 - view2;
            }
        });
        return questionList;
    }

    @Override
    public List<Question> getUserRaiseQuestion(Long userId) {
        return questionMapper.getQuestionRaise(userId);
    }

    @Override
    public boolean createQuestion(Question question, Long userId) {
        questionService.saveOrUpdate(question);
        return questionMapper.createUserQuestion(question.getId(),userId);
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionService.updateById(question);
    }
}




