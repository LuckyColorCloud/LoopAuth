package com.sobercoding.loopauth.exception;

import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.util.Optional;

/**
 * @author: Weny
 */
public class LoopAuthDaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoopAuthDaoException(LoopAuthExceptionEnum loopAuthExceptionEnum) {
        super(loopAuthExceptionEnum.getMsg());
    }

    public LoopAuthDaoException(LoopAuthExceptionEnum loopAuthExceptionEnum, String detailMsg) {
        super(String.format(loopAuthExceptionEnum.getMsg(),detailMsg));
    }
    /**
     * 判断数据是否为空
     * @author Sober
     * @param obj 对象
     * @param loopAuthExceptionEnum 错误枚举
     */
    public static void isEmpty(Object obj, LoopAuthExceptionEnum loopAuthExceptionEnum) {
        Optional.ofNullable(obj)
                .filter(LoopAuthUtil::isNotEmpty)
                .orElseThrow(() -> new LoopAuthDaoException(loopAuthExceptionEnum));
    }

    /**
     * 判断Redis缓存是否成功
     * @param obj 对象
     * @param loopAuthExceptionEnum 错误枚举
     */
    public static void isTrue(boolean obj, LoopAuthExceptionEnum loopAuthExceptionEnum) {
        Optional.of(obj)
                .filter(o -> o)
                .orElseThrow(() -> new LoopAuthDaoException(loopAuthExceptionEnum));
    }
}
