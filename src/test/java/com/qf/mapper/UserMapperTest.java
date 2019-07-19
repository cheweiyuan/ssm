package com.qf.mapper;

import com.qf.AcTests;
import com.qf.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * cwy 2019/7/15 22:04
 **/
public class UserMapperTest extends AcTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findCountByUsername() {
    }

    @Test
    @Transactional
    public void save() {
        User user = new User();
        user.setUsername("xxx");
        user.setPassword("xxxx");
        user.setPhone("111111");

        Integer count = userMapper.save(user);

        assertEquals(new Integer(1),count);
    }

    @Test
    public void  findByUsername(){
        User admin = userMapper.findByUsername("admin");
        System.out.println(admin);
    }
}