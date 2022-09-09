package com.sobercoding.loopauth.springbootstarter;

import com.sobercoding.loopauth.rbac.annotation.CheckPermission;
import com.sobercoding.loopauth.rbac.annotation.CheckRole;
import com.sobercoding.loopauth.session.annotation.CheckLogin;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 注解匹配类
 * @author Yun
 */
public class CheckPermissionAnnotation {
    private CheckPermissionAnnotation() {
    }

    /**
     * 获取注解 鉴权
     */
    public static Consumer<Method> checkMethodAnnotation = (method) -> {

        // 先校验 Method 所属 Class 上的注解
        CheckPermissionAnnotation.checkElementAnnotation.accept(method.getDeclaringClass());

        // 再校验 Method 上的注解
        CheckPermissionAnnotation.checkElementAnnotation.accept(method);
    };


    /**
     * 逐个匹配注解 解析
     */
    public static Consumer<AnnotatedElement> checkElementAnnotation = (target) -> {

        CheckLogin checkLogin = (CheckLogin) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAutoCheckLogin.class);
        if (checkLogin != null) {
            LoopAuthFaceImpl.isLogin();
        }

        CheckRole checkRole = (CheckRole) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAuthRole.class);
        if (checkRole != null) {
            LoopAuthFaceImpl.checkByRole(checkRole.mode(), checkRole.value());
        }

        CheckPermission checkPermission = (CheckPermission) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAuthPermission.class);
        if (checkPermission != null) {
            LoopAuthFaceImpl.checkByPermission(checkPermission.mode(), checkPermission.value());
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
