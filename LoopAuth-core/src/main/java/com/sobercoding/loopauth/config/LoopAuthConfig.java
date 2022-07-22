package com.sobercoding.loopauth.config;

import com.sobercoding.loopauth.model.constant.AccessMode;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 配置类数据模型
 * @create: 2022/07/20 20:13
 */
public class LoopAuthConfig implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * token名称 默认looptoken
     * 前后端分离项目 通过该Value在请求头获取token
     * 一体项目通过该Value获取cookie
     */
    private final String tokenName;

    /**
     * token有效期(单位毫秒) 默认7天、-1永久
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
     */
    private final AccessMode[] accessModes;

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

    public AccessMode[] getAccessModes() {
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

    /**
     * @Method: builder
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 返回构造器
     * @param
     * @Return:
     * @Exception:
     * @Date: 2022/7/21 0:54
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @program: LoopAuth
     * @author: Sober
     * @Description: LoopAuthConfig构造器
     * @create: 2022/07/20 20:13
     */
    public static class Builder {

        private String tokenName = "looptoken";

        private long timeOut = 60 * 60 * 24 * 7 * 1000;

        private Boolean isMutualism = false;

        private Boolean isExclusion = false;

        private Boolean isRenew = true;

        private int maxLoginCount = 1;

        private AccessMode[] accessModes = new AccessMode[] {AccessMode.COOKIE,AccessMode.HEADER};

        public Builder tokenName(String tokenName) {
            this.tokenName = tokenName;
            return this;
        }

        /**
         * @Method: timeOut
         * @Author: Sober
         * @Version: 0.0.1
         * @Description: timeOut采用long格式记录时间，
         * 单位为毫秒，客户端输入单位为秒，因此 * 1000
         * @param timeOut
         * @Return: com.sobercoding.loopauth.config.LoopAuthConfig.Builder
         * @Exception:
         * @Date: 2022/7/22 20:18
         */
        public Builder timeOut(long timeOut) {
            this.timeOut = timeOut * 1000;
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

        public Builder accessModes(AccessMode[] accessModes) {
            this.accessModes = accessModes;
            return this;
        }

        /**
         * @Method: build
         * @Author: Sober
         * @Version: 0.0.1
         * @Description: 构建结束创建LoopAuthConfig写入并返回
         * @param
         * @Return:
         * @Exception:
         * @Date: 2022/7/21 0:55
         */
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
