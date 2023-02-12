package com.sobercoding.loopauth.abac.annotation;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.abac.policy.model.Policy;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static Set<Policy> getPolicySet(CheckAbac checkAbac) {
        Policy policy = new Policy();
        policy.setName(checkAbac.name());
        List<AbacProperty> abacProperty = Arrays.asList(checkAbac.value());
        abacProperty.forEach(item -> {
            switch (item.prop()) {
                case ACTION:
                    policy.setActionProperty(item.name(), item.value());
                    break;
                case SUBJECT:
                    policy.setSubjectProperty(item.name(), item.value());
                    break;
                case CONTEXTUAL:
                    policy.setContextualProperty(item.name(), item.value());
                    break;
                case RES_OBJECT:
                    policy.setResObjectProperty(item.name(), item.value());
                    break;
                default:
                    break;
            }
        });
        Set<Policy> policies = new HashSet<>();
        policies.add(policy);
        return policies;
    }

}
