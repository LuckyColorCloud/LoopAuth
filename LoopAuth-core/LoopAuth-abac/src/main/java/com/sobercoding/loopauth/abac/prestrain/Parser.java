package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Set;

/**
 * 解析者
 * 解析指定目录下的所有class文件
 * 生成权限预加载方法到内存中
 * @author Sober
 */
public class Parser {


    /**
     * 运行解析器
     */
    public static void run() throws IOException {
        // 取得配置的包路径
//        String prestrainPackagePath = AbacStrategy.getAbacConfig().getPrestrainPackagePath();
        String prestrainPackagePath = "com.sobercoding.loopauth.abac.util";
        // 获取路径下的所有class文件
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(prestrainPackagePath.replace(".", "/"));
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
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
            System.out.println("解析完成");
        }
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
