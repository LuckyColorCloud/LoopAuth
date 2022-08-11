package com.sobercoding.loopauth.exception;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 异常类
 * @create: 2022/08/08 19:07
 */
public class LoopAuthException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    /**
     * 异常状态码
     */
    private int code;

    public LoopAuthException(){
        super(LoopAuthExceptionEnum.ERROR.getMsg());
        this.code = LoopAuthExceptionEnum.ERROR.getCode();
    }


    public LoopAuthException(LoopAuthExceptionEnum loopAuthExceptionEnum){
        super(loopAuthExceptionEnum.getMsg());
        this.code = loopAuthExceptionEnum.getCode();
    }


}
