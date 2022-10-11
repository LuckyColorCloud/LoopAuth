package com.sobercoding.loopauth.session.config;

import com.sobercoding.loopauth.session.model.TokenAccessMode;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 配置类
 * @author Sober
 */
public class SessionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token名称 默认looptoken
     * 前后端分离项目 通过该Value在请求头获取token
     * 一体项目通过该Value获取cookie
     */
    private String tokenName = "LoopAuth";

    /**
     * token有效期(单位毫秒) 默认24小时、-1永久
     */
    private long timeOut = 60 * 60 * 24 * 1000;

    /**
     * abac鉴权  默认不开启
     */
    private Boolean isAbac = false;

    /**
     * token共生  默认不开启
     * 开启则 账号可以同时在线
     */
    private Boolean isMutualism = false;

    /**
     * 互斥登录， 默认不开启
     * 开启则 多人操作相同设备登录 会互相挤掉线（只有在 isMutualism=true 时此配置才有效）
     */
    private Boolean isExclusion = false;

    /**
     * token持久化配置  默认不开启
     */
    private Boolean isTokenPersistence = false;

    /**
     * 同一账号最大登录数量 默认1
     * -1代表不限 （只有在 isMutualism=true 时此配置才有效）
     */
    private int maxLoginCount = 1;

    /**
     * 自动续签 默认打开
     * 每次操作，会自动刷新token有效期
     */
    private Boolean isRenew = true;

    /**
     * token获取方式 默认[AccessMode.COOKIE,AccessMode.HEADER]顺序获取
     * 即COOKIE中获取到鉴权成功，则不前往HEADER获取
     */
    private ConcurrentSkipListSet<TokenAccessMode> accessModes = new ConcurrentSkipListSet<>(Collections.singletonList(TokenAccessMode.COOKIE));

    /**
     * Token生成密钥
     */
    private String secretKey = "LoopAuth";

    /**
     * token持久层存储的前缀
     */
    private String tokenPersistencePrefix = "LoopAuthToken";

    /**
     * LoginId持久层存储的前缀
     */
    private String loginIdPersistencePrefix = "LoopAuthLoginId";


    public Boolean getAbac() {
        return isAbac;
    }

    public SessionConfig setAbac(Boolean abac) {
        isAbac = abac;
        return this;
    }

    public Boolean getTokenPersistence() {
        return isTokenPersistence;
    }

    public SessionConfig setTokenPersistence(Boolean tokenPersistence) {
        isTokenPersistence = tokenPersistence;
        return this;
    }


    public String getTokenPersistencePrefix() {
        return tokenPersistencePrefix;
    }

    public SessionConfig setTokenPersistencePrefix(String tokenPersistencePrefix) {
        this.tokenPersistencePrefix = tokenPersistencePrefix;
        return this;
    }

    public String getLoginIdPersistencePrefix() {
        return loginIdPersistencePrefix;
    }

    public SessionConfig setLoginIdPersistencePrefix(String loginIdPersistencePrefix) {
        this.loginIdPersistencePrefix = loginIdPersistencePrefix;
        return this;
    }

    public SessionConfig setTokenName(String tokenName) {
        this.tokenName = tokenName;
        return this;
    }

    public SessionConfig setTimeOut(long timeOut) {
        this.timeOut = timeOut * 1000;
        return this;
    }

    public SessionConfig setMutualism(Boolean mutualism) {
        isMutualism = mutualism;
        return this;
    }

    public SessionConfig setExclusion(Boolean exclusion) {
        isExclusion = exclusion;
        return this;
    }

    public SessionConfig setRenew(Boolean renew) {
        isRenew = renew;
        return this;
    }

    public SessionConfig setMaxLoginCount(int maxLoginCount) {
        this.maxLoginCount = maxLoginCount;
        return this;
    }

    public SessionConfig setAccessModes(ConcurrentSkipListSet<TokenAccessMode> accessModes) {
        this.accessModes = accessModes;
        return this;
    }

    public SessionConfig setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenName() {
        return tokenName;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public Boolean getMutualism() {
        return isMutualism;
    }

    public Boolean getExclusion() {
        return isExclusion;
    }

    public Boolean getRenew() {
        return isRenew;
    }

    public int getMaxLoginCount() {
        return maxLoginCount;
    }

    public Set<TokenAccessMode> getAccessModes() {
        return accessModes;
    }

    @Override
    public String toString() {
        return "LoopAuthConfig{" +
                "tokenName='" + tokenName + '\'' +
                ", timeOut=" + timeOut +
                ", isMutualism=" + isMutualism +
                ", isExclusion=" + isExclusion +
                ", isTokenPersistence=" + isTokenPersistence +
                ", maxLoginCount=" + maxLoginCount +
                ", isRenew=" + isRenew +
                ", accessModes=" + accessModes +
                ", secretKey='" + secretKey + '\'' +
                ", tokenPersistencePrefix='" + tokenPersistencePrefix + '\'' +
                ", loginIdPersistencePrefix='" + loginIdPersistencePrefix + '\'' +
                '}';
    }
}
