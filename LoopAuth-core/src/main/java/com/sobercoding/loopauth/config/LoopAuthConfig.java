package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.model.constant.TokenAccessMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 配置类数据模型
 * @create: 2022/07/20 20:13
 */
public class LoopAuthConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token名称 默认looptoken
     * 前后端分离项目 通过该Value在请求头获取token
     * 一体项目通过该Value获取cookie
     */
    private String tokenName = "LoopAuth";
    /**
     * token有效期(单位毫秒) 默认7天、-1永久
     */
    private long timeOut = 60 * 60 * 24 * 7 * 1000;

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
     * 即使COOKIE中获取到鉴权成功，则不前往HEADER获取
     */
    private ConcurrentSkipListSet<TokenAccessMode> accessModes = new ConcurrentSkipListSet<>(Arrays.asList(TokenAccessMode.COOKIE, TokenAccessMode.HEADER));

    /**
     * Token生成密钥
     */
    private String secretKey = "LoopAuth";

    /**
     * redis配置
     */
    private RedisConfig redisConfig = new RedisConfig();

    public LoopAuthConfig setTokenName(String tokenName) {
        this.tokenName = tokenName;
        return this;
    }

    public LoopAuthConfig setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public LoopAuthConfig setMutualism(Boolean mutualism) {
        isMutualism = mutualism;
        return this;
    }

    public LoopAuthConfig setExclusion(Boolean exclusion) {
        isExclusion = exclusion;
        return this;
    }

    public LoopAuthConfig setRenew(Boolean renew) {
        isRenew = renew;
        return this;
    }

    public LoopAuthConfig setMaxLoginCount(int maxLoginCount) {
        this.maxLoginCount = maxLoginCount;
        return this;
    }

    public LoopAuthConfig setAccessModes(ConcurrentSkipListSet<TokenAccessMode> accessModes) {
        this.accessModes = accessModes;
        return this;
    }

    public LoopAuthConfig setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public LoopAuthConfig setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
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

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    @Override
    public String toString() {
        return "LoopAuthConfig{" +
                "tokenName='" + tokenName + '\'' +
                ", timeOut=" + timeOut +
                ", isMutualism=" + isMutualism +
                ", isExclusion=" + isExclusion +
                ", maxLoginCount=" + maxLoginCount +
                ", isRenew=" + isRenew +
                ", accessModes=" + accessModes +
                ", secretKey='" + secretKey + '\'' +
                ", redisConfig=" + redisConfig +
                '}';
    }
}
