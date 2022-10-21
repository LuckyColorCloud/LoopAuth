package com.sobercoding.loopauth.context;

import com.sobercoding.loopauth.context.LoopAuthRequest;
import com.sobercoding.loopauth.context.LoopAuthResponse;
import com.sobercoding.loopauth.context.LoopAuthStorage;

/**
 * 获取上下文接口
 * @author Sober
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
