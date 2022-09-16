package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Answer;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.AnswerService;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author maomao
 * 2022/8/12 15:14
 */

@SpringBootTest
public class QuestionMapperTest {

    @Resource
    UserMapper userMapper;
    @Resource
    PassageMapper passageMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    QuestionService questionService;
    @Resource
    AnswerService answerService;
    @Resource
    UserService userService;

    @Test
    public void getManyQuestionAnswer() {
        User userinfo = userMapper.getUserinfoById(1L);
        System.out.println(userinfo.getQuestion().get(0).getAnswers());
//        List<Question> manyQuestion = questionMapper.getManyQuestion();
//        manyQuestion.forEach(System.out::println);
//        List<Question> questionRaise = questionMapper.getQuestionRaise(1L);
//        questionRaise.forEach(System.out::println);
//        List<User> manyUser = userMapper.getManyUser(1L);
//        manyUser.forEach(System.out::println);
//        List<Question> questionById = questionMapper.getManyQuestion();
//        questionById.forEach(System.out::println);
//        List<User> beFollowedByFollowId = userMapper.getBeFollowedByFollowId(2L);
//        beFollowedByFollowId.forEach(System.out::println);
    }

    @Test
    public void getQuestionByAnswerId() {
        //Question questionByAnswerId = questionMapper.getQuestionByQuestionId(1L);
        //Question question = questionService.getQuestionByQuestionId(1L);
        //System.out.println(question);
//        Answer answer = new Answer();
//        answer.setContent("写回答测试");
//        boolean save = answerService.createAnswer("写回答测试", 4L, 2L);
        List<User> beFollowedById = userService.getBeFollowedById(1L);
        beFollowedById.forEach(System.out::println);

    }
}