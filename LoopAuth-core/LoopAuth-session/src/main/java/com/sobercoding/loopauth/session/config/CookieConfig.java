package com.sobercoding.loopauth.session.config;

import java.io.Serializable;

/**
 * Cookie配置类
 * @author Sober
 */
public class CookieConfig implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 域
     */
    private String domain;


    /**
     * 路径
     */
    private String path = "/";

    /**
     * 是否长久有效 默认false 开启则cookie的有效时间为time-out,关闭则网页关闭后cookie丢失
     */
    private boolean remember;

    /**
     * 是否允许js操作
     */
    private boolean httpOnly;

    /**
     * 是否只在https安全协议传输
     */
    private boolean secure;

    /**
     * 安全等级
     * Strict 完全禁止第三方Cookie，跨站点时，任何情况下都不会发送Cookie
     * Lax 不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
     * None
     */
    private String sameSite;

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getDomain() {
        return domain;
    }

    public CookieConfig setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getPath() {
        return path;
    }

    public CookieConfig setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public CookieConfig setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public boolean isSecure() {
        return secure;
    }

    public CookieConfig setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    public String getSameSite() {
        return sameSite;
    }

    public CookieConfig setSameSite(String sameSite) {
        this.sameSite = sameSite;
        return this;
    }

    @Override
    public String toString() {
        return "CookieConfig{" +
                "domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", httpOnly=" + httpOnly +
                ", secure=" + secure +
                ", sameSite='" + sameSite + '\'' +
                '}';
    }
}
