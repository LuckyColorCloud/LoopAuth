package com.sobercoding.loopauth.config;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/19 21:56
 */
public class CookieConfig {

    /**
     * 域
     */
    private String domain;

    /**
     * 路径
     */
    private String path = "/";

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getSameSite() {
        return sameSite;
    }

    public void setSameSite(String sameSite) {
        this.sameSite = sameSite;
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
