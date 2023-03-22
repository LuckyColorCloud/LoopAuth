package com.sobercoding.loopauth.context;


import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;
import com.sobercoding.loopauth.util.LoopAuthUtil;

/**
 * @author Yun
 */
public interface SourceContext {

    /**
     * 获取资源
     * @return java.lang.Object
     * @author Yun
     */
    public Object getSource();


    /**
     * 参数为空时 返回 默认值
     *
     * @param value        值
     * @param defaultValue 默认值
     * @return java.lang.String
     * @author Yun
     */
    public default String getParam(String value, String defaultValue) {
        if (LoopAuthUtil.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 检测提供的参数是否为指定值
     *
     * @param paramValue 键
     * @param value      值
     * @return boolean
     * @author Yun
     */
    public default boolean isParam(String paramValue, String value) {
        return LoopAuthUtil.isNotEmpty(paramValue) && paramValue.equals(value);
    }

    /**
     * 检测请求是否提供了指定参数
     *
     * @param param 参数名称
     * @return boolean
     */
    public default boolean hasParam(String param) {
        return LoopAuthUtil.isNotEmpty(param);
    }

    /**
     * 在 [请求体] 里获取一个值 （此值必须存在，否则抛出异常 ）
     *
     * @param paramValue paramValue = getParam(name); 键
     * @return java.lang.String
     */
    public default String getParamNotNull(String paramValue) {
        if (LoopAuthUtil.isEmpty(paramValue)) {
            throw new LoopAuthParamException(LoopAuthExceptionEnum.PARAM_IS_NULL, "paramValue");
        }
        return paramValue;
    }

    /**
     * 在 [请求头] 里获取一个值
     *
     * @param value        值
     * @param defaultValue 值为空时的默认值
     * @return java.lang.String
     */
    public default String getHeader(String value, String defaultValue) {
        if (LoopAuthUtil.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }


}
