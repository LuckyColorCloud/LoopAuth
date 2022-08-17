package com.sobercoding.loopauth.context;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 获取上下文接口
 * @create: 2022/07/20 23:29
 */
public interface LoopAuthContext {
    /**
     * 获取当前请求的 [Request] 对象
     *
     * @return see note
     */
    LoopAuthRequest getRequest();

    /**
     * 获取当前请求的 [Response] 对象
     *
     * @return see note
     */
    LoopAuthResponse getResponse();

    /**
     * 获取当前请求的 [存储器] 对象
     *
     * @return see note
     */
    LoopAuthStorage getStorage();
}
