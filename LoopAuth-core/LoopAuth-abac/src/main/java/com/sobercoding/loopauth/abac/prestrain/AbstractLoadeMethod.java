package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;
import com.sobercoding.loopauth.function.VerifyFunction;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Supplier;

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
            loadFunc(clazz);
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
                    // 载入map中
                    if (clazz.isAnnotationPresent(VerifyPrestrain.class)) {
                        // 验证方法
                        try {
                            this.methodFactory.putVerify(calzzName + "." + func.getName(), (VerifyFunction<Object, Object>) func.get(clazz.newInstance()));
                        } catch (IllegalAccessException | InstantiationException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (clazz.isAnnotationPresent(PropertyPretrain.class)) {
                        // 属性方法
                        try {
                            // 属性方法
                            this.methodFactory.putProperty(calzzName + "." + func.getName(), (Supplier<Object>) func.get(clazz.newInstance()));
                        } catch (IllegalAccessException | InstantiationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }


}