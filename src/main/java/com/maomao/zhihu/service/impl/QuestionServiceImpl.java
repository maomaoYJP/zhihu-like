package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Question;
import com.maomao.zhihu.service.QuestionService;
import com.maomao.zhihu.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【question(问题)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




