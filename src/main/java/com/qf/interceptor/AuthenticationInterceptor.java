package com.qf.interceptor;

import com.qf.constant.SsmConstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实现拦截器的校验
 * cwy 2019/7/17 20:51
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {


    //前处理  执行controller方法之前
    //可以选择对请求进行拦截/放行
    //return flase ->  拦截   return run -> 放行
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //1 获取session
        HttpSession session = request.getSession();
        //2 通过session获取用户信息
        User user = (User) session.getAttribute(SsmConstant.USER);
        //3判断是否为null
        if (user == null) {
            //3.1 为null -> 用户未登录->跳转到登录页面->return false
            response.sendRedirect(request.getContextPath() + "/user/login-ui");
            return false;
        } else {
            //3.2  不为null -> return true;
            return true;
        }
    }

    //执行完controller 返回ModelAndView之后执行
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {


    }


    //拦截器处理器  响应数据之前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
