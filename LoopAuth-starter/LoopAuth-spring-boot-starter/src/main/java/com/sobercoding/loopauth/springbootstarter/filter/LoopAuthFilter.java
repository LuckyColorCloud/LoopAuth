package com.sobercoding.loopauth.springbootstarter.filter;

/**
 * 全局 认证过滤器
 *
 * @author Yun
 */
@FunctionalInterface
public interface LoopAuthFilter {

    /**
     * 认证执行方法
     * @param obj 扩展参
     */
     void run(Object obj);
}
