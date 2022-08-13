package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Comment;
import com.maomao.zhihu.service.CommentService;
import com.maomao.zhihu.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【comment(评论)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




