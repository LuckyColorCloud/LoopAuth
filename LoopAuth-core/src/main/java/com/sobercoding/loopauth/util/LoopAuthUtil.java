package com.sobercoding.loopauth.util;

/**
 * @author Yun
 */
public class LoopAuthUtil {

    /**
     * 判断是否为空对象 或 字符串
     *
     * @param object 对象
     * @return 是否
     */
    public static boolean isEmpty(Object object) {
        return object == null || "".equals(object);
    }

    /**
     * 判断是否不为空对象 或 字符串
     *
     * @param object 对象
     * @return 是否
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
