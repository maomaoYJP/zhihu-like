package com.maomao.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.entity.User;
import com.maomao.zhihu.mapper.TalkMapper;
import com.maomao.zhihu.service.UserService;
import com.maomao.zhihu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86155
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-08-12 13:04:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username).eq("password",password);
        User user = userService.getOne(wrapper);
        return user;
    }

    @Override
    public User getManyUserById(Long id) {
        return userMapper.getManyUserById(id);
    }

    @Override
    public List<User> getFollowsById(Long id) {
        return userMapper.getFollowsByUserId(id);
    }
}




