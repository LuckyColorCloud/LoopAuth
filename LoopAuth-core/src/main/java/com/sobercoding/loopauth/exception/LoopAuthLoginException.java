package com.sobercoding.loopauth.exception;

import java.util.Optional;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 会话异常
 * @create: 2022/08/08 19:24
 */
public class LoopAuthLoginException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthLoginException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }



    public static String notLogin(String loginObj){
        return Optional.ofNullable(loginObj)
                .orElseThrow(() -> new LoopAuthLoginException(LoopAuthExceptionEnum.LOGIN_NOT_EXIST));
    }
}
