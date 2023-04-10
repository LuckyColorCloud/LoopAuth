package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 加载方法
 * @author Sober
 */
public class AbstractLoadeMethod {

    /**
     * 方法存储
     */
    protected final MethodFactory methodFactory = new MethodFactory();

    /**
     * 加载方法
     *
     * @param path 类路径
     */
    protected void loadMethod(String path) {
        try {
            // 加载类
            Class<?> clazz = Class.forName(path);
            // 加载方法
            loadMethod(clazz);
        } catch (ClassNotFoundException e) {
            // TODO 异常处理
            e.printStackTrace();
        }
    }

    private void loadFunc(Class<?> clazz) {
        String calzzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
        // 获取当前类的所有方法  并行遍历  载入map中
        Arrays.stream(clazz.getFields())
                .forEach(func -> {
                });
    }

    private void loadMethod(Class<?> clazz) {
        String calzzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
        // 获取当前类的所有方法  并行遍历  载入map中
        Arrays.stream(clazz.getMethods())
                .forEach(method -> {
                    // 验证方法是否有异常
                    if (method.getExceptionTypes().length > 0 &&
                            // 验证方法是否是静态方法
                            !Modifier.isStatic(method.getModifiers()) &&
                            // 验证方法是否是public方法
                            !Modifier.isPublic(method.getModifiers()) &&
                            // 验证方法返回值必须是boolean类型
                            method.getReturnType() != boolean.class) {
                        return;
                    }
                    // 验证方法入参必须为两个String类型
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 2 ||
                            parameterTypes[0] != String.class ||
                            parameterTypes[1] != String.class) {
                        return;
                    }
                    // 载入map中
                    if (clazz.isAnnotationPresent(VerifyPrestrain.class)) {
                        // 验证方法
                        this.methodFactory.putVerify(calzzName + "." + method.getName(), method);
                    } else if (clazz.isAnnotationPresent(PropertyPretrain.class)) {
                        // 属性方法
                        this.methodFactory.putProperty(calzzName + "." + method.getName(), method);
                    }
                });
    }

}