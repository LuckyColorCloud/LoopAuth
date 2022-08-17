package com.sobercoding.loopauth.context;

import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/17 17:08
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
