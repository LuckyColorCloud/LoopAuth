package com.sobercoding.loopauth.exception;

/**
 * @author Yun
 */
public class NotRoleException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    /**
     * 异常状态码
     */
    private int code;

    public NotRoleException(){
        super(LoopAuthExceptionEnum.ERROR.getMsg());
        this.code = LoopAuthExceptionEnum.ERROR.getCode();
    }


    public NotRoleException(LoopAuthExceptionEnum loopAuthExceptionEnum){
        super(loopAuthExceptionEnum.getMsg());
        this.code = loopAuthExceptionEnum.getCode();
    }
}
