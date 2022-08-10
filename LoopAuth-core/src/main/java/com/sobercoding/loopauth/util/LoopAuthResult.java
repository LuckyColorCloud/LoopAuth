package com.sobercoding.loopauth.util;

import java.io.Serializable;


/**
 * 封装结果集
 * @author Hao
 */
public class LoopAuthResult implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    private int code;
    private String message;
    private Object data;

    public LoopAuthResult() {
    }

    public LoopAuthResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // 构建成功
    public static LoopAuthResult success(){
        return new LoopAuthResult(SUCCESS,"OK",null);
    }
    public static LoopAuthResult success(String message){
        return new LoopAuthResult(SUCCESS,message,null);
    }
    public static LoopAuthResult success(Object data){
        return new LoopAuthResult(SUCCESS,"OK",data);
    }
    // 构建失败
    public static LoopAuthResult error(){
        return new LoopAuthResult(ERROR,"ERROR",null);
    }
    public static LoopAuthResult error(String message){
        return new LoopAuthResult(ERROR,message,null);
    }

    // 构建自定义
    public static LoopAuthResult custom(int code, String message, Object data){
        return new LoopAuthResult(code,message,data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{"
                + "\"code\": " + this.code
                + ", \"message\": " + transValue(this.message)
                + ", \"data\": " + transValue(this.data)
                + "}";
    }
    private String transValue(Object value) {
        if(value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }
}
