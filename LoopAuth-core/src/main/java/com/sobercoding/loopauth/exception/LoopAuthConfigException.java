package com.sobercoding.loopauth.exception;


/**
 * @author: Sober
 */
public class LoopAuthConfigException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthConfigException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }


}
