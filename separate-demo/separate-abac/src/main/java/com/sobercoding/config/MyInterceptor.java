package com.sobercoding.config;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
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
        // 执行abac鉴权
        LoopAuthAbac.check(request.getRequestURI(),request.getMethod());
        // 通过验证
        return true;
    }

}
