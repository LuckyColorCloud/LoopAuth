package com.sobercoding.loopauth.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yun
 */
public enum LoopAuthHttpMode {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, CONNECT,

    /**
     * 代表全部请求方式
     */
    ALL;

    private static final Map<String, LoopAuthHttpMode> MAP = new HashMap<>();

    static {
        for (LoopAuthHttpMode reqMethod : values()) {
            MAP.put(reqMethod.name(), reqMethod);
        }
    }


    public boolean equals(String method) {
        return this.name().equalsIgnoreCase(method);
    }

    public static LoopAuthHttpMode toEnum(String method) {
        return MAP.get(method);
    }
}
