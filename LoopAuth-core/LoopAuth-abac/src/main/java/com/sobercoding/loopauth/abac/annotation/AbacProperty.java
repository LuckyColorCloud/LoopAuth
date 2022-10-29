package com.sobercoding.loopauth.abac.annotation;

/**
 * ABAC属性注解
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
}
