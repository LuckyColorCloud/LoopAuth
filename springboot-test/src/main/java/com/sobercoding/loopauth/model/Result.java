package com.sobercoding.loopauth.model;

import java.io.Serializable;


/**
 * 封装结果集
 * @author Hao
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // 构建成功
    public static Result success(){
        return new Result(SUCCESS,"OK",null);
    }
    public static Result success(String message){
        return new Result(SUCCESS,message,null);
    }
    public static Result success(Object data){
        return new Result(SUCCESS,"OK",data);
    }
    // 构建失败
    public static Result error(){
        return new Result(ERROR,"ERROR",null);
    }
    public static Result error(String message){
        return new Result(ERROR,message,null);
    }

    // 构建自定义
    public static Result custom(int code, String message, Object data){
        return new Result(code,message,data);
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
