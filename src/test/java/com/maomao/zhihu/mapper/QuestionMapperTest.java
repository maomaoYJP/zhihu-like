package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.queryvo.QuestionAnswer;
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
    QuestionMapper questionMapper;

    @Test
    public void getManyQuestionAnswer() {
        List<Question> manyQuestionAnswer = questionMapper.getManyQuestionAnswer();
        manyQuestionAnswer.forEach(System.out::println);
    }
}