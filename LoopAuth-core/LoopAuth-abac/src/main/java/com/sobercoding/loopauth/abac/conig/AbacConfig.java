package com.sobercoding.loopauth.abac.conig;

import java.io.Serializable;

/**
 * ABAC配置类
 * @author Sober
 */
public class AbacConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需要预加载的权限验证包路径
     */
    private String prestrainPackagePath;

    public String getPrestrainPackagePath() {
        return prestrainPackagePath;
    }

    public AbacConfig setPrestrainPackagePath(String prestrainPackagePath) {
        this.prestrainPackagePath = prestrainPackagePath;
        return this;
    }
}
