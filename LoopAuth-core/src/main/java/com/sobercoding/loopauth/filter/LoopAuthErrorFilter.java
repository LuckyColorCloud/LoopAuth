package com.sobercoding.loopauth.filter;

/**
 * 全局 错误认证过滤器
 * @author Yun
 */
public interface LoopAuthErrorFilter {

    /**
     * 错误处理
     * @param throwable 异常
     * @return 结果
     */
    Object run(Throwable throwable);

}
