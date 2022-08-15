package com.sobercoding.loopauth.annotation;

import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限认证：必须具有指定权限才能进入该方法
 * <p> 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 * @author Yun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LoopAuthPermission {

    /**
     * 需要校验的权限码
     * 删除  增加  更新 查询 权限
     *
     * @return 需要校验的权限码
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR | non，默认AND
     *
     * @return 验证模式
     */
    LoopAuthVerifyMode mode() default LoopAuthVerifyMode.AND;

    /**
     * 多账号体系下所属的账号体系标识
     *
     * @return see note
     */
    String type() default "";


}
