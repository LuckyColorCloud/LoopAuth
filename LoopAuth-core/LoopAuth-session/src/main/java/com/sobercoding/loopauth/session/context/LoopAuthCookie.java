package com.sobercoding.loopauth.session.context;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Cookie生成
 * @author Sober
 */
public class LoopAuthCookie {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 过期时间
     */
    private long maxAge = SessionStrategy.getLoopAuthConfig().getTimeOut() / 1000L;

    /**
     * 域
     */
    private String domain = SessionStrategy.getLoopAuthConfig().getCookieConfig().getDomain();

    /**
     * 路径
     */
    private String path = SessionStrategy.getLoopAuthConfig().getCookieConfig().getPath();

    /**
     * 是否允许js操作
     */
    private boolean httpOnly = SessionStrategy.getLoopAuthConfig().getCookieConfig().isHttpOnly();

    /**
     * 是否只在https安全协议传输
     */
    private boolean secure = SessionStrategy.getLoopAuthConfig().getCookieConfig().isSecure();

    /**
     * 安全等级
     * Strict 完全禁止第三方Cookie，跨站点时，任何情况下都不会发送Cookie
     * Lax 不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
     * None
     */
    private String sameSite = SessionStrategy.getLoopAuthConfig().getCookieConfig().getSameSite();

    public String getName() {
        return name;
    }

    public LoopAuthCookie setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public LoopAuthCookie setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public LoopAuthCookie setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public LoopAuthCookie setMaxAge(long maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public String getPath() {
        return path;
    }

    public LoopAuthCookie setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public LoopAuthCookie setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public boolean isSecure() {
        return secure;
    }

    public LoopAuthCookie setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    public String getSameSite() {
        return sameSite;
    }

    public LoopAuthCookie setSameSite(String sameSite) {
        this.sameSite = sameSite;
        return this;
    }

    /**
     * 转换为响应头 Set-Cookie 参数需要的值
     * @return java.lang.String
     */
    public String toCookieString() {
        LoopAuthParamException.isNotEmpty(name, LoopAuthExceptionEnum.PARAM_IS_NULL, "Cookie Name");
        LoopAuthParamException.isNotEmpty(value, LoopAuthExceptionEnum.PARAM_IS_NULL, "Cookie Value");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("=").append(value);

        if (SessionStrategy.getLoopAuthConfig().getCookieConfig().isRemember()){
            if(maxAge >= 0) {
                stringBuilder.append("; Max-Age=").append(maxAge);
                String expires;
                if(maxAge == 0) {
                    expires = Instant.EPOCH.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);
                } else {
                    expires = Instant.ofEpochMilli(System.currentTimeMillis()).plusSeconds(maxAge)
                            .atOffset(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME);
                }
                stringBuilder.append("; Expires=").append(expires);
            }
        }
        if(LoopAuthUtil.isNotEmpty(domain)) {
            stringBuilder.append("; Domain=").append(domain);
        }
        if(LoopAuthUtil.isNotEmpty(path)) {
            stringBuilder.append("; Path=").append(path);
        }
        if(secure) {
            stringBuilder.append("; Secure");
        }
        if(httpOnly) {
            stringBuilder.append("; HttpOnly");
        }
        if(LoopAuthUtil.isNotEmpty(sameSite)) {
            stringBuilder.append("; sameSite=").append(sameSite);
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "LoopAuthCookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", domain='" + domain + '\'' +
                ", maxAge=" + maxAge +
                ", path='" + path + '\'' +
                ", httpOnly=" + httpOnly +
                ", secure=" + secure +
                ", sameSite='" + sameSite + '\'' +
                '}';
    }
}
