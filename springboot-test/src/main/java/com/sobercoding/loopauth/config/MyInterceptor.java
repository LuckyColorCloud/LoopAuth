package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.session.annotation.CheckSessionAnnotation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sober
 */
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 注解拦截
        CheckSessionAnnotation.checkMethodAnnotation.accept(((HandlerMethod) handler).getMethod());
        // 通过验证
        return true;
    }

}
