package com.sobercoding.loopauth.function;

/**
 * @author: Sober
 */
@FunctionalInterface
public interface LrFunction<V,T> {

    /**
     * 返回值参
     * @author Sober
     * @param v 值
     * @param t 参
     * @return V
     */
    V exe(V v, T t);

}
