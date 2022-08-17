package com.sobercoding.loopauth.exception;

/**
 * 必要参数异常
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/17 16:49
 */
public class LoopAuthParamException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthParamException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }

}
