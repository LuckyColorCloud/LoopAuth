package com.sobercoding.loopauth.springbootstarter.interceptor;

import com.sobercoding.loopauth.servlet.context.LoopAuthRequestForServlet;
import com.sobercoding.loopauth.servlet.context.LoopAuthResponseForServlet;
import com.sobercoding.loopauth.springbootstarter.LoopAuthRouteFunction;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            function.run(new LoopAuthRequestForServlet(request), new LoopAuthResponseForServlet(response), handler);
        } catch (Exception e) {
            // 停止匹配，向前端输出结果
            if(response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(e.getMessage());
            return false;
        }

        // 通过验证
        return true;
    }

}
