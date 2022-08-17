package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/17 16:46
 */
public class LoopAuthConfigException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthConfigException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }


}
