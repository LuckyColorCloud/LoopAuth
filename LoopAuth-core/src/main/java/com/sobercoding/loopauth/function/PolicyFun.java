package com.sobercoding.loopauth.function;

/**
 * ABAC执行策略
 * @author Sober
 */
@FunctionalInterface
public interface PolicyFun<V, T> {

    /**
     * 返回策略匹配结果
     * @author Sober
     * @param v 属性值
     * @param t 属性规则
     * @return boolean
     */
    boolean mate(V v, T t);
}
