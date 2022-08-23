package com.sobercoding.loopauth.context;

import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

/**
 * 上下文默认类
 * @author Sober
 */
public class LoopAuthContextDefaultImpl implements LoopAuthContext{
    @Override
    public LoopAuthRequest getRequest() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.INITIALIZATION_FAILURE);
    }

    @Override
    public LoopAuthResponse getResponse() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.INITIALIZATION_FAILURE);
    }

    @Override
    public LoopAuthStorage getStorage() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.INITIALIZATION_FAILURE);
    }
}
