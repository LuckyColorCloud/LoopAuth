package com.sobercoding.loopauth.exception;

/**
 * 许可异常
 * @author Yun
 */
public class LoopAuthPermissionException extends LoopAuthException {
    private static final long serialVersionUID = 1L;

    public LoopAuthPermissionException(LoopAuthExceptionEnum loopAuthExceptionEnum){
        super(loopAuthExceptionEnum);
    }
    public LoopAuthPermissionException(LoopAuthExceptionEnum loopAuthExceptionEnum, String detailMsg) {
        super(loopAuthExceptionEnum);
    }

}
