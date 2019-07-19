package com.qf.service;

import com.qf.pojo.User;

/**
 * cwy 2019/7/15 14:51
 **/
public interface UserService {

    //根据用户名查询是否可用
    Integer checkUsername(String username);

    //注册功能
    Integer register(User user);

    User login(String username,String password);
}
