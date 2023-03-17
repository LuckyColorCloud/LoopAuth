package com.sobercoding.loopauth.abac.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 预加载属性注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyPretrain {

    /**
     * 需要扫描的属性类路径
     * @return
     */
    String[] value() default {};
}
