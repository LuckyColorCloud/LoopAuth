package com.sobercoding.loopauth;

/**
 * ABAC执行策略
 * @author Sober
 */
public interface PolicyFun<V> {

    /**
     * 返回策略匹配结果
     * @author Sober
     * @param v 值
     * @return boolean
     */
    boolean mate(V v);
}
