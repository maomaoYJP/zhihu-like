package com.maomao.zhihu.service;

import com.maomao.zhihu.entity.Talk;
import com.maomao.zhihu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86155
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-08-12 13:04:07
*/
public interface UserService extends IService<User> {

    //登录验证
    User checkUser(String username,String password);

    //根据id获得用户详细信息
    User getManyUserById(Long id);

    //根据id获得用户的关注信息
    List<User> getFollowsById(Long id);

    //根据id获得用户粉丝
    List<User> getBeFollowedById(Long id);

    //根据id添加粉丝
    boolean addFollowById(Long userId, Long followId);

    //根据id删除粉丝
    boolean removeFollowById(Long userId, Long followId);
}
