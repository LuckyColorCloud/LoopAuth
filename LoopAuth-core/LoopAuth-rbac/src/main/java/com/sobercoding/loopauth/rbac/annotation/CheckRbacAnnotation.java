package com.sobercoding.loopauth.rbac.annotation;

import com.sobercoding.loopauth.rbac.carryout.LoopAuthRbac;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 注解匹配类
 * @author Yun
 */
public class CheckRbacAnnotation {
    private CheckRbacAnnotation() {
    }

    /**
     * 获取注解 鉴权
     */
    public static Consumer<Method> checkMethodAnnotation = (method) -> {

        // 先校验 Method 所属 Class 上的注解+
        CheckRbacAnnotation.checkElementAnnotation.accept(method.getDeclaringClass());

        // 再校验 Method 上的注解
        CheckRbacAnnotation.checkElementAnnotation.accept(method);
    };


    /**
     * 逐个匹配注解 解析
     */
    public static Consumer<AnnotatedElement> checkElementAnnotation = (target) -> {

        CheckRole checkRole = (CheckRole) CheckRbacAnnotation.getAnnotation.apply(target, CheckRole.class);
        if (checkRole != null) {
            LoopAuthRbac.checkByRole(checkRole.mode(), checkRole.value());
        }

        CheckPermission checkPermission = (CheckPermission) CheckRbacAnnotation.getAnnotation.apply(target, CheckPermission.class);
        if (checkPermission != null) {
            LoopAuthRbac.checkByPermission(checkPermission.mode(), checkPermission.value());
        }

    };


    /**
     * 从元素上获取注解
     */
    @SuppressWarnings("Convert2MethodRef")
    public static BiFunction<AnnotatedElement, Class<? extends Annotation>, Annotation> getAnnotation = (element, annotationClass) -> {
        // 默认使用jdk的注解处理器
        return element.getAnnotation(annotationClass);
    };

}
