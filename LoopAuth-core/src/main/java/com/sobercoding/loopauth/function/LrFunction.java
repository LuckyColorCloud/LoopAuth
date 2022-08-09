package com.sobercoding.loopauth.function;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/10 00:16
 */
@FunctionalInterface
public interface LrFunction<V,T> {

    /**
     * @Method: exe
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 返回值参
     * @param v 值
     * @param t 参
     * @Return:
     * @Exception:
     * @Date: 2022/8/10 0:18
     */
    V exe(V v, T t);

}
