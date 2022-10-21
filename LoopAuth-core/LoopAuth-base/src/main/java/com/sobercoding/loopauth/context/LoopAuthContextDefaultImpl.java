package com.sobercoding.loopauth.context;

import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.context.LoopAuthRequest;
import com.sobercoding.loopauth.context.LoopAuthResponse;
import com.sobercoding.loopauth.context.LoopAuthStorage;
import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

/**
 * 上下文默认类
 * @author Sober
 */
public class LoopAuthContextDefaultImpl implements LoopAuthContext {
    @Override
    public LoopAuthRequest getRequest() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED,"LoopAuthContext.getRequest()");
    }

    @Override
    public LoopAuthResponse getResponse() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED,"LoopAuthContext.getResponse()");
    }

    @Override
    public LoopAuthStorage getStorage() {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED,"LoopAuthContext.getStorage()");
    }
}
