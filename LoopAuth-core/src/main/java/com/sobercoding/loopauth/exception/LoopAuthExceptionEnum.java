package com.sobercoding.loopauth.exception;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 异常统一枚举
 * @create: 2022/08/08 19:28
 */
public enum LoopAuthExceptionEnum {
    // 未知错误
    ERROR(500, "未知错误"),
    // HTTP请求方式不对
    HTTP_MODULE_ERROR(415, "HTTP请求方式不对"),
    //请求参数 不能为 必须有值
    PARAM_IS_NULL(100001, "必要请求参数不存在:"),
    //无此角色权限
    NO_ROLE(500002, "本账户无此角色:"),
    //无权限
    NO_PERMISSION(500003, "无权限访问本路径"),
    // 非法请求
    LOGIN_NOT_EXIST(401, "非法请求");

    /**
     * 异常状态码
     */
    private int code;

    /**
     * 异常消息
     */
    private String msg;

    LoopAuthExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public LoopAuthExceptionEnum setMsg(String msg) {
        this.msg = this.msg + msg;
        return this;
    }

    public int getCode() {
        return code;
    }
}
