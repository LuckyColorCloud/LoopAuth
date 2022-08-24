package com.sobercoding.loopauth.exception;


/**
 * @author: Sober
 */
public class LoopAuthException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    /**
     * 异常状态码
     */
    private int code;

    public LoopAuthException(LoopAuthExceptionEnum loopAuthExceptionEnum){
        super(loopAuthExceptionEnum.getMsg());
        this.code = loopAuthExceptionEnum.getCode();
    }

    public LoopAuthException(LoopAuthExceptionEnum loopAuthExceptionEnum, String detailMsg){
        super(String.format(loopAuthExceptionEnum.getMsg(),detailMsg));
        this.code = loopAuthExceptionEnum.getCode();
    }


}
