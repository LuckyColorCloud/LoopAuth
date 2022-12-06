package com.sobercoding.loopauth.function;

import java.util.function.Supplier;

@FunctionalInterface
public interface MateFunction<V, R> {

    /**
     * 返回策略匹配结果
     * @author Sober
     * @param v 获取属性值
     * @param r 属性规则
     */
    boolean mate(Supplier <V> v, R r);
}
