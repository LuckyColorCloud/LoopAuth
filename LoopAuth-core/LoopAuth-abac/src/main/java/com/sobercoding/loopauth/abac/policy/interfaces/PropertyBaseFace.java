package com.sobercoding.loopauth.abac.policy.interfaces;

import com.sobercoding.loopauth.function.MateFunction;

import java.util.Arrays;

/**
 * 访问者、环境属性 基类
 * @author sober
 */
public interface PropertyBaseFace<V, R> {
    /**
     * 相等
     * @return MateFunction
     */
    default MateFunction<V, R> eq() {
        return (value, rule) -> value.get().equals(rule);
    }

    /**
     * 相等 模糊匹配
     * @return MateFunction
     */
    default MateFunction<V, R> fuzzyEq() {
        return Object::equals;
    }


    /**
     * 闭区间
     * @return MateFunction
     */
    default MateFunction<V, R> between(){
        return (value, rule) -> {
            String val = (String) value.get();
            String[] rules = ((String) rule).split("~");
            return val.compareTo(rules[0]) >= 0 && val.compareTo(rules[1]) <= 0;
        };
    }

    /**
     * 包含
     * @return MateFunction
     */
    default MateFunction<V, R> in(){
        return (value, rule) -> {
            String val = (String) value.get();
            String[] rules = ((String) rule).split(",");
            return Arrays.asList(rules).contains(val);
        };
    }
}
