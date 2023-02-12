package com.sobercoding.loopauth.abac.policy.model;

import com.sobercoding.loopauth.function.MateFunction;

import java.util.HashMap;
import java.util.function.Function;

/**
 * 被访问者对象属性
 * @author sober
 */
public class ResObject<T> {
    /**
     * 存储值来源方法对应key为字段名
     */
    private HashMap<String, Function<? super T, ?>> functionHashMap = new HashMap<>();

    /**
     * 存储匹配方式
     */
    private HashMap<String, MateFunction<?, String>> mateFunctionHashMap = new HashMap<>();
    public HashMap<String, Function<? super T, ?>> getFunctionHashMap() {
        return functionHashMap;
    }

    public HashMap<String, MateFunction<?, String>> getMateFunctionHashMap() {
        return mateFunctionHashMap;
    }
    private <S> ResObject(String fieldName,
                      Function<? super T,? extends S> func,
                      MateFunction<S, String> mateFunc) {
        this.functionHashMap.put(fieldName,func);
        this.mateFunctionHashMap.put(fieldName,mateFunc);
    }


    /**
     * 构建主题属性规则
     * @param fieldName 与abac对于字段名
     * @param func 值的来源方法引用
     * @param mateFunc 匹配规则
     * @return
     * @param <S>
     */
    public <S> ResObject<T> structure(String fieldName,
                                   Function<? super T, ? extends S> func,
                                   MateFunction<S,String> mateFunc) {
        functionHashMap.put(fieldName,func);
        mateFunctionHashMap.put(fieldName,mateFunc);
        return this;
    }

    public static <S,T> ResObject<T> init(String fieldName,
                                       Function<? super T, ? extends S> func,
                                       MateFunction<S, String> mateFunc) {
        return new ResObject<>(fieldName, func, mateFunc);
    }

}
