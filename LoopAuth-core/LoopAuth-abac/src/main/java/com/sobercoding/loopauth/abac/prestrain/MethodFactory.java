package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.function.VerifyFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 方法工厂
 * @author Sober
 */
public class MethodFactory {

    /**
     * 验证方法
     */
    private final Map<String, VerifyFunction<Object, Object>> verifyMap = new ConcurrentHashMap<>();

    /**
     * 属性方法
     */
    private final Map<String, Supplier<Object>> propertyMap = new ConcurrentHashMap<>();

    /**
     * 载入验证方法
     * @param key
     * @param func
     */
    public void putVerify(String key, VerifyFunction<Object, Object> func) {
        verifyMap.put(key, func);
    }

    /**
     * 载入属性方法
     * @param key
     * @param func
     */
    public void putProperty(String key, Supplier<Object> func) {
        propertyMap.put(key, func);
    }

    /**
     * get
     * @return
     */
    public Map<String, VerifyFunction<Object, Object>> getVerifyMap() {
        return verifyMap;
    }

    /**
     * get
     * @return
     */
    public Map<String, Supplier<Object>> getPropertyMap() {
        return propertyMap;
    }
}
