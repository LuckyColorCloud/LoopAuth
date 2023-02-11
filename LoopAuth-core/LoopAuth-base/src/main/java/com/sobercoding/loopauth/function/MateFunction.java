package com.sobercoding.loopauth.function;

import java.util.function.Supplier;

/**
 * 匹配函数
 * @param <V>
 * @param <R>
 * @author sober
 */
@FunctionalInterface
public interface MateFunction<V, R> {

    /**
     * 返回策略匹配结果
     * @author Sober
     * @param v 获取属性值
     * @param r 属性规则
     * @return boolean
     */
    boolean mate(Supplier <V> v, R r);
}
