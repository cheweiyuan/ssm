package com.qf.mapper;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * cwy 2019/7/15 19:31
 **/
public interface UserMapper {

    //1 根据用户名查询数据条数
    Integer findCountByUsername(@Param("username")String username );

    //2添加用户信息
    Integer save(User user);

    //3 根据用户名查询用户信息
    User findByUsername(@Param("username")String username);
}
