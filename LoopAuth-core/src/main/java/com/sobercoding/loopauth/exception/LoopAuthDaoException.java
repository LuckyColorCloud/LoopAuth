package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author: Weny
 * @date: 2022/8/20
 * @see: com.sobercoding.loopauth.exception
 * @version: v1.0.0
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
