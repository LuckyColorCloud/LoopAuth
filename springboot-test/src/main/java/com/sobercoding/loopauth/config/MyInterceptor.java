package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.session.annotation.CheckSessionAnnotation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Sober
 */
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            // 注解拦截
            CheckSessionAnnotation.checkMethodAnnotation.accept(method);
        }
        // 通过验证
        return true;
    }

}
