package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Passage;
import com.maomao.zhihu.service.PassageService;
import com.maomao.zhihu.mapper.PassageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86155
* @description 针对表【passage(文章)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class PassageServiceImpl extends ServiceImpl<PassageMapper, Passage>
    implements PassageService{

    @Resource
    PassageMapper passageMapper;

    @Override
    public List<Passage> getAllPassage() {
        return passageMapper.getManyPassage();
    }
}




