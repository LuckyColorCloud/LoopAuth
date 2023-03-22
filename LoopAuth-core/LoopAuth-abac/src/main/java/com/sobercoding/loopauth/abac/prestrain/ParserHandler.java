package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析者
 * 解析指定目录下的所有class文件
 * 生成权限预加载方法到内存中
 * @author Sober
 */
public abstract class ParserHandler {

    /**
     * 下一个执行节点
     */
    protected ParserHandler next;

    public ParserHandler(ParserHandler next) {
        this.next = next;
    }

    /**
     * 解析
     * @param paths
     */
    protected abstract void resolution(String ...paths);


    /**
     * 得到class对象
     */
    public static Class<?> [] getClazz() throws IOException {
        // 取得配置的包路径
//        String prestrainPackagePath = AbacStrategy.getAbacConfig().getPrestrainPackagePath();
        String prestrainPackagePath = "com.sobercoding.loopauth.abac.util";
        // 获取路径下所有条目
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(prestrainPackagePath.replace(".", "/"));
        while (resources.hasMoreElements()) {
            // 获取当前条目
            URL url = resources.nextElement();
            // 创建JarURLConnection对象
            // 获取所有文件
            String[] file = new File(url.getFile()).list();
            Class<?>[] classes = new Class[file.length];
            for (int i = 0; i < file.length; i++) {
                try {
                    // 当前class对象
                    Class<?> clazz = Class.forName(prestrainPackagePath + "." + file[i].replaceAll("\\.class", ""));
                    // 验证clazz是否包含注解VerifyPrestrain
                    VerifyPrestrain verifyPrestrain = clazz.getAnnotation(VerifyPrestrain.class);
                    if (verifyPrestrain != null) {
                        // 获取当前类
                        classes[i] = clazz;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return classes;
        }
        return null;
    }

    public static Map<String, Method> getMethodByClazzs(Class<?> [] clazzs) {
        // 创建一个ABAC权限预加载方法的map
        Map<String, Method> methodMap = new ConcurrentHashMap<>();
        // 遍历class对象
        for (Class<?> clazz : clazzs) {
            // 当前类名称首字母小写
            String clazzName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
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
                        methodMap.put(clazzName + "." + method.getName(), method);
                    });
        }
        return methodMap;
    }


    public static void main(String[] args) throws IOException {
        // 得到class对象
        Class<?> [] clazzs = Optional.ofNullable(getClazz()).orElse(new Class[0]);
        // 存储方法
        Map<String, Method> methodMap = getMethodByClazzs(clazzs);
        // 打印Key
        methodMap.keySet().forEach(System.out::println);
    }
}
