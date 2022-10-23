package com.sobercoding.loopauth.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yun
 */
public enum LoopAuthHttpMode {
    /**
     * GET请求
     */
    GET,
    /**
     * HEAD请求
     */
    HEAD,
    /**
     * POST请求
     */
    POST,
    /**
     * PUT请求
     */
    PUT,
    /**
     * PATCH请求
     */
    PATCH,
    /**
     * DELETE请求
     */
    DELETE,
    /**
     * OPTIONS请求
     */
    OPTIONS,
    /**
     * TRACE请求
     */
    TRACE,
    /**
     * CONNECT请求
     */
    CONNECT,

    /**
     * 代表全部请求方式
     */
    ALL;

    /**
     * 字符串与枚举对应
     */
    private static final Map<String, LoopAuthHttpMode> MAP = new HashMap<>();

    static {
        for (LoopAuthHttpMode reqMethod : values()) {
            MAP.put(reqMethod.name(), reqMethod);
        }
    }


    /**
     * 对比请求方式
     * @author Sober
     * @param method 请求方式
     * @return boolean
     */
    public boolean equals(String method) {
        return this.name().equalsIgnoreCase(method);
    }

    /**
     * 字符串请求方式 转 枚举
     * @author Sober
     * @param method 请求方式
     * @return LoopAuthHttpMode
     */
    public static LoopAuthHttpMode toEnum(String method) {
        return MAP.get(method);
    }
}
