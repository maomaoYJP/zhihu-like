package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.queryvo.QuestionAnswer;
import com.maomao.zhihu.service.QuestionService;
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

    @Test
    public void getManyQuestionAnswer() {
//        List<User> manyUser = userMapper.getManyUser(1L);
//        manyUser.forEach(System.out::println);
//        List<Question> questionById = questionMapper.getManyQuestion();
//        questionById.forEach(System.out::println);
    }
}