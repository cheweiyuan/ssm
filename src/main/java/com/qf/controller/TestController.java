package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cwy 2019/7/18 10:14
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    @PutMapping("/update")
    public String update(String name,Integer age){

        System.out.println("test测试");
        return null;
    }
}
