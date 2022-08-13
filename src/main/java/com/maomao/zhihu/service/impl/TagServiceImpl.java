package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Tag;
import com.maomao.zhihu.service.TagService;
import com.maomao.zhihu.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【tag(标签)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




