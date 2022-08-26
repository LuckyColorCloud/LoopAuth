package com.sobercoding.loopauth.servlet.filter;

/**
 * 全局 认证过滤器
 *
 * @author Yun
 */
@FunctionalInterface
public interface LoopAuthFilter {

    /**
     * 认证执行方法
     * @param isIntercept 是否拦截
     * @param route 路由
     */
     void run(boolean isIntercept, String route);
}
