package com.sobercoding.loopauth.abac.prestrain;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法工厂
 * @author Sober
 */
public class MethodFactory {

    /**
     * 验证方法
     */
    private final Map<String, Method> verifyMap = new ConcurrentHashMap<>();

    /**
     * 属性方法
     */
    private final Map<String, Method> propertyMap = new ConcurrentHashMap<>();

    /**
     * 载入验证方法
     * @param key
     * @param func
     */
    public void putVerify(String key, Method func) {
        verifyMap.put(key, func);
    }

    /**
     * 载入属性方法
     * @param key
     * @param func
     */
    public void putProperty(String key, Method func) {
        propertyMap.put(key, func);
    }

    /**
     * get
     * @return
     */
    public Map<String, Method> getVerifyMap() {
        return verifyMap;
    }

    /**
     * get
     * @return
     */
    public Map<String, Method> getPropertyMap() {
        return propertyMap;
    }
}
