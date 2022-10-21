package com.sobercoding.loopauth.function;

/**
 * 全局 认证过滤器
 *
 * @author Yun
 */
@FunctionalInterface
public interface LoopAuthFilterFun {

    /**
     * 认证执行方法
     * @param isIntercept 是否拦截
     * @param route 路由
     */
     void run(boolean isIntercept, String route);
}
