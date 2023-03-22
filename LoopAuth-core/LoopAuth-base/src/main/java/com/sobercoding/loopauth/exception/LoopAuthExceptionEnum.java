package com.sobercoding.loopauth.exception;

/**
 * 异常统一枚举
 *
 * @author: Sober
 */
public enum LoopAuthExceptionEnum {
    // HTTP请求方式不对
    HTTP_MODULE_ERROR(415, "HTTP请求方式不对"),
    // 非法请求
    LOGIN_NOT_EXIST_F(401, "非法请求: %s"),
    // 非法请求
    LOGIN_NOT_EXIST(401, "非法请求"),
    //无权限
    NO_PERMISSION(403, "未经许可"),
    NO_PERMISSION_F(403, "未经许可: %s"),
    //请求参数 不能为 必须有值
    PARAM_IS_NULL(100001, "必要请求参数不存在: %s"),
    // 数据异常
    DATA_EXCEPTION(200001, "持久层数据异常"),
    CACHE_FAILED(200002, "持久层操作失败"),
    DATA_REDIS_LINK(200003, "Redis持久层链接异常"),
    // 初始化失败
    CONFIGURATION_UNREALIZED(500005, "项目配置异常: %s 未实现Or未加载");

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
