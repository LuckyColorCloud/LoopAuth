package com.sobercoding.loopauth.exception;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 异常统一枚举
 * @create: 2022/08/08 19:28
 */
public enum LoopAuthExceptionEnum {
    // HTTP请求方式不对
    HTTP_MODULE_ERROR(415, "HTTP请求方式不对"),
    // 非法请求
    LOGIN_NOT_EXIST(401, "非法请求"),
    // 非法请求
    LOGIN_EXPIRE(401, "登录过期"),
    //无权限
    NO_PERMISSION(401, "未经许可"),
    //请求参数 不能为 必须有值
    PARAM_IS_NULL(100001, "必要请求参数不存在:"),
    // 数据异常
    DATA_EXCEPTION(200001, "数据异常"),
    CACHE_FAILED(200002, "数据缓存失败"),
    // 初始化失败
    INITIALIZATION_FAILURE(500005, "项目配置异常,初始化失败");

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
