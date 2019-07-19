package com.qf.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * cwy 2019/7/18 10:59
 **/
@ControllerAdvice
@Component
public class MyExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String ex(Exception ex){
        ex.printStackTrace();
        return "error";
    }
}
