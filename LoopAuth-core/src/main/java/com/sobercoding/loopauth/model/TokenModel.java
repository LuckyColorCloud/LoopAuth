package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.LoopAuthStrategy;
import java.io.Serializable;
import java.util.Objects;

/**
 * token模型
 * @author: Sober
 */
public class TokenModel implements Serializable,Comparable<TokenModel>  {

    private static final long serialVersionUID = 1L;

    /**
     * token值
     */
    private String value;

    /**
     * 用户id
     */
    private String loginId;

    /**
     * 设备类型
     */
    private String facility = "";

    /**
     * 设备名称
     */
    private String facilityName = "";

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * token有效期(单位毫秒)
     */
    private long timeOut;

    public String getFacilityName() {
        return facilityName;
    }

    public TokenModel setFacilityName(String facilityName) {
        this.facilityName = facilityName;
        return this;
    }

    public String getLoginId() {
        return loginId;
    }

    public TokenModel setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public TokenModel setValue(String value) {
        this.value = value;
        return this;
    }

    public TokenModel setFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public TokenModel setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public TokenModel setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return this;
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

    /**
     * 对内存的直接操作
     * 获取内存中当前token的loginId
     * @author Sober
     * @return long
     */
    public String gainLongId(){
        return (String) LoopAuthStrategy.getLoopAuthDao()
                .get(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                        ":" +
                        value);
    }

    /**
     * 对内存的直接操作
     * 获取内存中当前token的TokenModel
     */
    public void depositTokenModel(){
        LoopAuthStrategy.getLoopAuthDao()
                .set(
                        LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() + ":" + value,
                        loginId,
                        timeOut
                );
    }

    /**
     * 对内存的直接操作，删除当前token会话
     * @author Sober
     */
    public void remove(){
        LoopAuthStrategy.getLoopAuthDao()
                .remove(LoopAuthStrategy.getLoopAuthConfig().getTokenPersistencePrefix() +
                        ":" +
                        value);
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
    public int hashCode() {
        return Objects.hash(value);
    }
    @Override
    public int compareTo(TokenModel obj) {
        return Integer.compare(this.hashCode() - obj.hashCode(), 0);
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "value='" + value + '\'' +
                ", loginId='" + loginId + '\'' +
                ", facility='" + facility + '\'' +
                ", createTime=" + createTime +
                ", timeOut=" + timeOut +
                '}';
    }
}
