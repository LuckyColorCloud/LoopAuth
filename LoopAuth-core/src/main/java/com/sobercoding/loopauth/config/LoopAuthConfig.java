package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.model.type.AccessMode;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 配置类数据模型
 * @create: 2022/07/20 20:13
 */
public class LoopAuthConfig implements Serializable{

    private static final long serialVersionUID = -6541180061782004705L;

    /**
     * token名称 默认looptoken
     * 前后端分离项目 通过该Value在请求头获取token
     * 一体项目通过该Value获取cookie
     */
    private final String tokenName;

    /**
     * token有效期(单位秒) 默认7天、-1永久
     */
    private final long timeOut;

    /**
     * token共生  默认不开启
     * 开启则 账号可以同时在线
     */
    private final Boolean isMutualism;

    /**
     * 互斥登录， 默认不开启
     * 开启则 多人操作相同设备登录 会互相挤掉线
     */
    private final Boolean isExclusion;

    /**
     * 自动续签 默认打开
     * 每次操作，会自动刷新token有效期
     */
    private final Boolean isRenew;

    /**
     * 同一账号最大登录数量 默认1
     * -1代表不限 （只有在 isMutualism=true 时此配置才有效）
     */
    private final int maxLoginCount;

    /**
     * token获取方式 默认[AccessMode.COOKIE,AccessMode.HEADER]顺序获取
     * 即使COOKIE中获取到鉴权成功，则不前往HEADER获取
     * 原子类保证可见性
     */
    private final AtomicReferenceArray<AccessMode> accessModes;

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

    public AtomicReferenceArray<AccessMode> getAccessModes() {
        return accessModes;
    }

    private LoopAuthConfig(Builder builder) {
        this.tokenName = builder.tokenName;
        this.timeOut = builder.timeOut;
        this.isMutualism = builder.isMutualism;
        this.isExclusion = builder.isExclusion;
        this.isRenew = builder.isRenew;
        this.maxLoginCount = builder.maxLoginCount;
        this.accessModes = builder.accessModes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String tokenName = "looptoken";

        private long timeOut = 60 * 60 * 24 * 7;

        private Boolean isMutualism = false;

        private Boolean isExclusion = false;

        private Boolean isRenew = true;

        private int maxLoginCount = 1;

        private AtomicReferenceArray<AccessMode> accessModes = new AtomicReferenceArray<>(new AccessMode[] {AccessMode.COOKIE,AccessMode.HEADER});

        public Builder tokenName(String tokenName) {
            this.tokenName = tokenName;
            return this;
        }

        public Builder timeOut(long timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder mutualism(Boolean mutualism) {
            isMutualism = mutualism;
            return this;
        }

        public Builder exclusion(Boolean exclusion) {
            isExclusion = exclusion;
            return this;
        }

        public Builder renew(Boolean renew) {
            isRenew = renew;
            return this;
        }

        public Builder maxLoginCount(int maxLoginCount) {
            this.maxLoginCount = maxLoginCount;
            return this;
        }

        public Builder accessModes(AtomicReferenceArray<AccessMode> accessModes) {
            this.accessModes = accessModes;
            return this;
        }

        public LoopAuthConfig build(){
            return new LoopAuthConfig(this);
        }
    }


    @Override
    public String toString() {
        return "LoopAuthConfig{" +
                "tokenName='" + tokenName + '\'' +
                ", timeOut=" + timeOut +
                ", isMutualism=" + isMutualism +
                ", isExclusion=" + isExclusion +
                ", isRenew=" + isRenew +
                ", maxLoginCount=" + maxLoginCount +
                ", accessModes=" + accessModes +
                '}';
    }


}
