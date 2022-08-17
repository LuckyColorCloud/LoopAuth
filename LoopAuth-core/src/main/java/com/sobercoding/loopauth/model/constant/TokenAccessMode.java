package com.sobercoding.loopauth.model.constant;

import com.sobercoding.loopauth.LoopAuthStrategy;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 认证获取方式枚举
 * @create: 2022/07/20 21:19
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
