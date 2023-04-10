package com.sobercoding.loopauth.function;

/**
 * 验证方法
 * @author Sober
 */
@FunctionalInterface
public interface VerifyFunction<V, R> {

    /**
     * 返回策略匹配结果
     * @author Sober
     * @param v 获取属性值
     * @param r 属性规则
     * @return boolean
     */
    boolean mate(V v, R r);
}
