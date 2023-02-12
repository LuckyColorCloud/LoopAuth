package com.sobercoding.loopauth.abac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ABAC认证
 * <p> 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 * @author Sober
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CheckAbac {

    /**
     * 拦截昵称
     *
     * @return see note
     */
    String name() default "";

    /**
     * 多账号体系下所属的账号体系标识
     *
     * @return see note
     */
    String type() default "";

    /**
     * 属性列表
     *
     * @return 需要校验的权限码
     */
    AbacProperty[] value() default {};

}
