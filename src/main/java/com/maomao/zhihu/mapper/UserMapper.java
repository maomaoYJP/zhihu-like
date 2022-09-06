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
    User getUserById(@Param("user_id")Long id);

    //通过id获得问题属于哪个用户
    User getPassageUserById(@Param("question_id")Long questionId);

    //通过id查询用户关注
    List<User> getFollowsByUserId(@Param("id")Long id);

    //通过id查询用户粉丝
    List<User> getBeFollowedByFollowId(Long id);

    //根据id获得用户详细信息
    User getUserinfoById(@Param("id") Long id);

    //根据id添加粉丝
    boolean addFollowById(Long userId, Long followId);

    //根据id删除粉丝
    boolean removeFollowById(Long userId, Long followId);

    //根据 answerId 和 userId 创建对应关系
    boolean createUserAnswerMap(Long answerId, Long userId);
}




