package com.sobercoding.loopauth.abac.util.scanner;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * 资源工具类
 * @author Sober
 */
public class ResourcesUtils {

    /**
     * 获取指定路径下的所有资源的磁盘路径
     * @param path 包路径
     * @return 磁盘路径
     * @throws IOException
     */
    public static Enumeration<URL> getResources(String path) throws IOException {
        return ClassLoader.getSystemResources(path);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }


}
