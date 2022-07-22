package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.config.LoopAuthConfig;

import java.io.Serializable;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: token模型
 * @create: 2022/07/22 20:06
 */
public class TokenModel implements Serializable {

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


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
