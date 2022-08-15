package com.maomao.zhihu.mapper;

import com.maomao.zhihu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86155
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2022-08-12 13:04:07
* @Entity com.maomao.zhihu.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    //通过id查询用户
    User getUserById(@Param("user_id")Long userId);

    //通过id查询用户粉丝
    List<User> getFollowsByUserId(@Param("id")Long id);

    //获得所有用户信息
    List<User> getManyUser();
}




