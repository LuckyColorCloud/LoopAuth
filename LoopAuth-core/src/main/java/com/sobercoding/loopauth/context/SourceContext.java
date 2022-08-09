package com.sobercoding.loopauth.context;


import com.sobercoding.loopauth.exception.LoopAuthException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.util.LoopAuthUtil;

/**
 * @author Yun
 */
public interface SourceContext {
    /**
     * 获取底层源对象
     * @return
     */
    public Object getSource();


    /**
     * 参数为空时 返回 默认值
     * @param value
     * @param defaultValue
     * @return
     */
    public default String getParam(String value, String defaultValue) {
        if(LoopAuthUtil.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 检测提供的参数是否为指定值
     * @param paramValue 键
     * @param value 值
     * @return 是否相等
     */
    public default boolean isParam(String paramValue, String value) {
        return LoopAuthUtil.isNotEmpty(paramValue) && paramValue.equals(value);
    }

    /**
     * 检测请求是否提供了指定参数
     * @param param 参数名称
     * @return 是否提供
     */
    public default boolean hasParam(String param) {
        return LoopAuthUtil.isNotEmpty(param);
    }

    /**
     * 在 [请求体] 里获取一个值 （此值必须存在，否则抛出异常 ）
     * @param         paramValue paramValue = getParam(name); 键
     * @return 参数值
     */
    public default String getParamNotNull(String paramValue) {
        if(LoopAuthUtil.isEmpty(paramValue)) {
            throw new LoopAuthException(LoopAuthExceptionEnum.PARAM_IS_NULL.setMsg(paramValue));
        }
        return paramValue;
    }

    /**
     * 在 [请求头] 里获取一个值
     * @param value 值
     * @param defaultValue 值为空时的默认值
     * @return 值
     */
    public default String getHeader(String value, String defaultValue) {
        if(LoopAuthUtil.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }


}
