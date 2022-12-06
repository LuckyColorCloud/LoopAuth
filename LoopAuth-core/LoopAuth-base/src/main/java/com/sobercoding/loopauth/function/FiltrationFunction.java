package com.sobercoding.loopauth.function;

/**
 * 过滤
 * @author sober
 * @param <V>
 * @param <R>
 */
@FunctionalInterface
public interface FiltrationFunction<V, R> {

    /**
     * 过滤
     * @param v 值
     * @return R 过滤后的数据
     */
    R get(V v);
}
