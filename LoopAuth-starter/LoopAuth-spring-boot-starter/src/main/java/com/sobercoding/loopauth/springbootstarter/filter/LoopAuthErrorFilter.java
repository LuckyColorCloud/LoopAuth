package com.sobercoding.loopauth.springbootstarter.filter;

/**
 * 全局 错误认证过滤器
 * @author Yun
 */
@FunctionalInterface
public interface LoopAuthErrorFilter {

    /**
     * 错误处理
     * @param throwable 异常
     * @return 结果
     */
    Object run(Throwable throwable);

}
