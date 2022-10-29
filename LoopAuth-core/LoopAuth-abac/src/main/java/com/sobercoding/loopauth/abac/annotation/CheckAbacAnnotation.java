package com.sobercoding.loopauth.abac.annotation;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author Sober
 */
public class CheckAbacAnnotation {

    private CheckAbacAnnotation() {
    }

    /**
     * 获取注解 鉴权
     */
    public static Consumer<Method> checkMethodAnnotation = (method) -> {

        // 先校验 Method 所属 Class 上的注解+
        CheckAbacAnnotation.checkElementAnnotation.accept(method.getDeclaringClass());

        // 再校验 Method 上的注解
        CheckAbacAnnotation.checkElementAnnotation.accept(method);
    };


    /**
     * 逐个匹配注解 解析
     */
    public static Consumer<AnnotatedElement> checkElementAnnotation = (target) -> {

        CheckAbac checkAbac = (CheckAbac) CheckAbacAnnotation.getAnnotation.apply(target, CheckAbac.class);
        if (checkAbac != null) {
            LoopAuthAbac.check(checkAbac);
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
