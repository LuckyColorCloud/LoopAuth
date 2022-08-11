package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.List;
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

    public static void isEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum){
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthLoginException(loopAuthExceptionEnum));
    }

    public static void isTrue(boolean obj, LoopAuthExceptionEnum loopAuthExceptionEnum){
        if (!obj){
            throw new LoopAuthLoginException(loopAuthExceptionEnum);
        }
    }


}
