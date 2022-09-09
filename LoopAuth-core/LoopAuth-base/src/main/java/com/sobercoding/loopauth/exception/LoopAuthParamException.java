package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

/**
 * 必要参数异常
 * @author: Sober
 */
public class LoopAuthParamException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoopAuthParamException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum.getMsg());
    }


    public LoopAuthParamException(LoopAuthExceptionEnum loopAuthExceptionEnum, String detailMsg) {
        super(String.format(loopAuthExceptionEnum.getMsg(),detailMsg));
    }

    public static void isNotEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum){
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthParamException(loopAuthExceptionEnum));
    }

    public static void isNotEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum, String detailMsg){
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthParamException(loopAuthExceptionEnum, detailMsg));
    }



}
