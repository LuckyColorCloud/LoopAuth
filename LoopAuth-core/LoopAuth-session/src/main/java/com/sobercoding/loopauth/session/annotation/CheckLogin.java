package com.sobercoding.loopauth.session.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证：只有登录之后才能进入该方法 
 *
 * @author Yun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface CheckLogin {

    /**
     * 多账号体系下所属的账号体系标识
     * @return see note
     */
    String type() default "";

}
