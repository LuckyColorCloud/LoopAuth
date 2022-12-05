package com.sobercoding.loopauth.function;

/**
 * 过滤
 * @param <V>
 * @param <R>
 */
@FunctionalInterface
public interface FiltrationFunction<V, R> {

    /**
     * 过滤
     * @return
     */
    R get(V v);
}
