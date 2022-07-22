package com.sobercoding.loopauth.model.constant;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 认证获取类型枚举
 * @create: 2022/07/20 21:19
 */
public enum AccessMode {

    /**
     * 请求头获取token认证
     */
    HEADER,
    /**
     * Cookie获取token认证
     */
    COOKIE;

}
