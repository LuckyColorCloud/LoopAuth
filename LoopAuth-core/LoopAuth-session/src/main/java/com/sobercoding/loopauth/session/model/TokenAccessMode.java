package com.sobercoding.loopauth.session.model;

/**
 * 认证获取方式枚举
 * @author: Sober
 */
public enum TokenAccessMode {

    /**
     * 请求头获取token
     */
    HEADER,
    /**
     * Cookie获取token
     */
    COOKIE;

}
