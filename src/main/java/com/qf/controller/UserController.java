package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.SendSMSUtil;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.qf.constant.SsmConstant.*;

/**
 * 用户模块的controller层
 * cwy 2019/7/15 10:28
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //发送短信工具类
    @Autowired
    private SendSMSUtil sendSMSUtil;

    //跳转到注册页面
    @GetMapping("/register-ui")
    public String registerUI() {
        //转发到注册页面
        return "/user/register";
    }

    //1 json数据需要反序列化程pojo对象
    //jsoblib   较老的工具   第三方依赖过多     当pojo类过于复杂时，序列化可能会出现问题
    //jackson   spring默认使用的工具       三个依赖        当pojo类过于复杂时，序列化可能会出现问题
    //gson      谷歌的json工具       一个依赖            没有后面的问题
    //jackson和gson和spring framework可以配合0配置整合
    //fastJSON      阿里巴巴的json工具         一个依赖      当pojo类过于复杂时，序列化可能会出现问题
    //              fastJSON号称自己是最快的json序列化工具

    //页面发送json数据时 接收的数据类型  ->  map.pojo类

    //http://localhost/user/check-username
    @PostMapping("/check-username")
    @ResponseBody   //不走视图解析器 直接响应  如果返回结果为Map/pojo类  自动序列化成json
    public ResultVO checkUsername(@RequestBody User user) {

        //1调用service 判断用户名是否可用
        Integer count = userService.checkUsername(user.getUsername());

        //2 使用RsultVO响应数据
        return new ResultVO(0, "成功", count);

    }


    //返送短信
    @PostMapping(value = "/send-sms", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String sendSMS(@RequestParam String phone, HttpSession session) {
        //1调用工具发短信
        String result = sendSMSUtil.sendSMS(session, phone);
        //2将result响应给ajax引擎
        return result;
    }

    //执行注册
    @PostMapping("register")
    public String register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session, RedirectAttributes redirectAttributes) {
        //1 校验验证码
        if (!StringUtils.isEmpty(registerCode)) {
            //验证码不为空
            Object attribute = session.getAttribute(CODE);
            if (!registerCode.equals("123")) {
                //验证码不正确
                redirectAttributes.addAttribute("registerInfo", "验证码错误!");
                return REDIRECT + REGISTER_UI;
            }

        }
        //2 校验参数是否合法
        if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)) {
            //参数不合法
            String msg = bindingResult.getFieldError().getDefaultMessage();
            if (StringUtils.isEmpty(msg)) {
                msg = "验证码为必填项，岂能不填！";
            }
            redirectAttributes.addAttribute("registerInfo", msg);
            return REDIRECT + REGISTER_UI;
        }
        //3 调用service执行注册功能
        Integer count = userService.register(user);

        //4  根据service返回的结果跳转到指定页面
        if (count == 1) {
            //注册成功
            return REDIRECT + LOGIN_UI;
        } else {
            //注册失败
            redirectAttributes.addAttribute("registerInfo", "服务器爆炸了！！");
            return REDIRECT + REGISTER_UI;
        }
    }

    //  跳转到登录页面
    //GET
    @GetMapping("/login-ui")
    public String loginUI() {
//        int i = 1 / 0;
        return "user/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username, String password, HttpSession session) {
        //1校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //参数不合法
            return new ResultVO(1, "用户名和密码为必填项", null);
        }
        //2调用service执行登录
        User user = userService.login(username, password);
        //3根据service返回单结果判断登录是否成功
        if (user != null) {
            //4如果成功 将用户的信息方法放入session域中
            session.setAttribute(USER, user);
            return new ResultVO(0, "登录成功", null);
        } else {
            //5如果失败 响应错误信息给ajax引擎
            return new ResultVO(2, "用户名或密码输入错误", null);

        }
    }
}