package com.sobercoding.loopauth.abac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 预加载验证注解
 * @author Yun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VerifyPrestrain {

    /**
     * 需要扫描的验证类路径
     * @return
     */
    String[] value() default {};
}
