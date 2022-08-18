package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

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

    public static void isNotEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum){
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthParamException(loopAuthExceptionEnum));
    }

}
