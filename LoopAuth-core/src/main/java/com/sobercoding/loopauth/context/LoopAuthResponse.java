package com.sobercoding.loopauth.context;

/**
 * Response 包装类 
 *
 * @author Yun
 */
public interface LoopAuthResponse extends SourceContext {

    /**
     * 设置响应状态码
     * @param sc 响应状态码
     * @return 链式编程
     */
    public LoopAuthResponse setStatus(int sc);

    /**
     * 在响应头里写入一个值
     * @param name 名字
     * @param value 值
     * @return 链式编程
     */
    public LoopAuthResponse setHeader(String name, String value);

    /**
     * 在响应头里添加一个值
     * @param name 名字
     * @param value 值
     * @return 链式编程
     */
    public LoopAuthResponse addHeader(String name, String value);
}
