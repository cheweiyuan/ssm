package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * cwy 2019/7/15 14:51
 **/
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer checkUsername(String username) {
        //健壮性代码
        if (!StringUtils.isEmpty(username)) {
            username = username.trim();
        }
        Integer count = userMapper.findCountByUsername(username);
        return count;
    }

    @Override
    public Integer register(User user) {

        //1对密码进行加密
        String newPwd = new Md5Hash(user.getPassword(), null, 1024).toString();
        user.setPassword(newPwd);
        //2调用mapper保存数据
        Integer count = userMapper.save(user);
        //3返回信息
        return count;

    }

    @Override
    public User login(String username, String password) {
        //根据用户名查询用户信息
        User user = userMapper.findByUsername(username);
        if (user != null) {
            // 判断查询的结果是否为null
            String newPwd = new Md5Hash(password, null, 1024).toString();
            //若果不为null  判断密码
            if(password!=null) {
                //如果密码正确  直接返回查询到的user
                if(user.getPassword().equals(newPwd)) {
                    return user;
                }
                return null;
            }
        }
        //其他情况统一返回null
        return null;
    }
}
