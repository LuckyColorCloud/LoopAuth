package com.sobercoding.config;

import com.sobercoding.loopauth.rbac.annotation.CheckRbacAnnotation;
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
        // 只有非静态资源请求才执行
        if(handler instanceof HandlerMethod) {
            // 强转HandlerMethod
            Method method = ((HandlerMethod) handler).getMethod();
            // 注解拦截
            CheckRbacAnnotation.checkMethodAnnotation.accept(method);
        }
        // 通过验证
        return true;
    }

}
