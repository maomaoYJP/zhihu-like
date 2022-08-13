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
    QuestionMapper questionMapper;

    @Override
    public List<Question> getAllAnswerCard() {
        return questionMapper.getManyQuestionAnswer();
    }

    @Override
    public List<Question> getQuestionRank() {
        List<Question> questionList = questionMapper.getManyQuestionAnswer();
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
}




