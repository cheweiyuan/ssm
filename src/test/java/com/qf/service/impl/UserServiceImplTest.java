package com.qf.service.impl;

import com.qf.AcTests;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * cwy 2019/7/15 20:02
 **/
public class UserServiceImplTest extends AcTests {

    @Autowired
    private UserService userService;

    @Test
    public void checkUsername() {
        Integer count = userService.checkUsername("admin");

        System.out.println(count);
    }

    @Test
    @Transactional
    public void register() {
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("xxxx");
        user.setPhone("111111");

        Integer count = userService.register(user);

        assertEquals(new Integer(1),count);
    }
}