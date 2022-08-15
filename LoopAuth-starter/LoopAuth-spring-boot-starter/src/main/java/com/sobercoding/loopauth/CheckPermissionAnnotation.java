package com.sobercoding.loopauth;

import com.sobercoding.loopauth.annotation.LoopAuthPermission;
import com.sobercoding.loopauth.annotation.LoopAuthRole;
import com.sobercoding.loopauth.annotation.LoopAutoCheckLogin;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
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

        LoopAutoCheckLogin checkLogin = (LoopAutoCheckLogin) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAutoCheckLogin.class);
        if (checkLogin != null) {
            LoopAuthFaceImpl.isLoginNow();
        }

        LoopAuthRole checkRole = (LoopAuthRole) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAuthRole.class);
        if (checkRole != null) {
            LoopAuthFaceImpl.checkByRole(checkRole.mode(), checkRole.value());
        }

        LoopAuthPermission checkPermission = (LoopAuthPermission) CheckPermissionAnnotation.getAnnotation.apply(target, LoopAuthPermission.class);
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


    public static boolean matchPaths(Collection<String> paths, ServletRequest request) {
        if (request == null || paths.isEmpty()) {
            return false;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getServletPath();
        for (String temp : paths) {
            if (LoopAuthUtil.fuzzyMatching(temp, path)) {
                return true;
            }
        }
        return false;
    }
}
