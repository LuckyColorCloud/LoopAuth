package com.sobercoding.loopauth.function;

/**
 * @author Sober
 */
@FunctionalInterface
public interface LoopAuthWebFluxFilterFun<E> {
    /**
     * 认证执行方法
     * @param isIntercept 是否拦截
     * @param route 路由
     */
    void run(boolean isIntercept, String route, E e);
}
