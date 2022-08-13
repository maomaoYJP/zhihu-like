package com.maomao.zhihu.mapper;


import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.entity.Talk;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author maomao
 * 2022/8/12 18:38
 */
@SpringBootTest
public class TalkMapperTest {
    @Resource
    TalkMapper talkMapper;
    @Resource
    PassageMapper passageMapper;

    @Test
    public void getAllTalk() {
//        List<Talk> allTalk = talkMapper.getAllTalk();
//        allTalk.forEach(System.out::println);
        List<Passage> allPassageCard = passageMapper.getAllPassageCard();
        allPassageCard.forEach(System.out::println);
    }
}