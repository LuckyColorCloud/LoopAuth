package com.sobercoding.loopauth.springbootstarter.interceptor;

import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.servlet.context.LoopAuthRequestForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthResponseForServlet;
import com.sobercoding.loopauth.springbootstarter.LoopAuthRouteFunction;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class LoopAuthInterceptor implements HandlerInterceptor {

    /**
     * 每次进入拦截器的[执行函数]
     */
    public LoopAuthRouteFunction function;


    /**
     * 创建一个拦截器
     */
    public LoopAuthInterceptor() {
    }


    // ----------------- 验证方法 -----------------

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 匹配是否拦截And鉴权
        function.run(new LoopAuthRequestForServlet(request), new LoopAuthResponseForServlet(response), handler);
        // 通过验证
        return true;
    }

}
