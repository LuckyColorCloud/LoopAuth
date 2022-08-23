package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

/**
 * @author: Weny
 */
public class LoopAuthDaoException extends LoopAuthException{

    private static final long serialVersionUID = 1L;

    public LoopAuthDaoException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum);
    }

    /**
     * 判断数据是否为空
     * @param obj
     * @param loopAuthExceptionEnum
     */
    public static void isEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum) {
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthLoginException(loopAuthExceptionEnum));
    }

    /**
     * 判断Redis缓存是否成功
     * @param obj
     * @param loopAuthExceptionEnum
     */
    public static void isTrue(boolean obj, LoopAuthExceptionEnum loopAuthExceptionEnum) {
        Optional.of(obj)
                .filter(o -> o)
                .orElseThrow(() -> new LoopAuthLoginException(loopAuthExceptionEnum));
    }
}
