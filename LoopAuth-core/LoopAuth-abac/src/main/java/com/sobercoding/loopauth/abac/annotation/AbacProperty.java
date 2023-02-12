package com.sobercoding.loopauth.abac.annotation;

import com.sobercoding.loopauth.abac.policy.model.PropertyEnum;

/**
 * ABAC操作类型注解
 * @author zcszc
 */
public @interface AbacProperty {
    /**
     * 属性名称
     */
    String name();

    /**
     * 属性值
     *
     */
    String value();

    /**
     * 类型
     * @return
     */
    PropertyEnum prop();
}
