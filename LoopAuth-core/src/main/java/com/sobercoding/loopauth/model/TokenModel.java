package com.sobercoding.loopauth.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: token模型
 * @create: 2022/07/22 20:06
 */
public class TokenModel implements Serializable,Comparable<TokenModel> {

    private static final long serialVersionUID = 1L;

    /**
     * token值
     */
    private String value;

    /**
     * 设备值
     */
    private String facility;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * token有效期(单位毫秒)
     */
    private long timeOut;

    public TokenModel() {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public String getValue() {
        return value;
    }

    public String getFacility() {
        return facility;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getTimeOut() {
        return timeOut;
    }



    private TokenModel(Builder builder) {
        this.value = builder.value;
        this.facility = builder.facility;
        this.timeOut = builder.timeOut;
        this.createTime = builder.createTime;
    }

    public static Builder builder() {
        return new TokenModel.Builder();
    }

    public static class Builder {
        /**
         * @program: LoopAuth
         * @author: Sober
         * @Description: TokenModel构造器
         * @create: 2022/7/23 21:56
         */

        private String value;

        private String facility;

        private long createTime;

        private long timeOut;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder facility(String facility) {
            this.facility = facility;
            return this;
        }

        public Builder createTime(long createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder timeOut(long timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        /**
         * @Method: build
         * @Author: Sober
         * @Version: 0.0.1
         * @Description: 构建结束创建TokenModel写入并返回
         * @param:
         * @Return:
         * @Exception:
         * @Date: 2022/7/21 0:55
         */
        public TokenModel build(){
            return new TokenModel(this);
        }


    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        TokenModel tokenModel = (TokenModel) obj;

        return Objects.equals(this.value, tokenModel.value);
    }

    @Override
    public int compareTo(TokenModel obj) {
        return Integer.compare(this.hashCode() - obj.hashCode(), 0);
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "value='" + value + '\'' +
                ", facility='" + facility + '\'' +
                ", createTime=" + createTime +
                ", timeOut=" + timeOut +
                '}';
    }
}
