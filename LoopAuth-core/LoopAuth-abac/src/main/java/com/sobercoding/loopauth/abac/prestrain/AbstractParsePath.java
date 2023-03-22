package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 解析路径
 * @author Sober
 */
public abstract class AbstractParsePath extends AbstractLoadeMethod {

    /**
     * 最终解析得到的所有class路径
     */
    protected final Set<String> paths = new HashSet<>();

    /**
     * 解析路径
     * @param path
     */
    protected Set<String> perse(String path) {
        verify(path);
        return path.charAt(path.length() - 1) != '*' ? perseByCalss(path) : perseByPackage(path);
    }

    private Set<String> perseByPackage(String path) {
        Set<String> paths = new HashSet<>();
        int index = path.lastIndexOf(".");
        try {
            Enumeration<URL> resources = ClassLoader.getSystemResources(path.substring(0, index));
            while (resources.hasMoreElements()) {
                // 获取当前条目
                URL url = resources.nextElement();
                // 获取所有文件
                String[] file = new File(url.getFile()).list();
                Class<?>[] classes = new Class[file.length];
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO
            throw new RuntimeException(e);
        }
        return paths;
    }

    private Set<String> perseByCalss(String path) {
        URL url = ClassLoader.getSystemResource(path);
        return url != null ? new HashSet<String>(){{this.add(path);}} : new HashSet<String>();
    }

    /**
     * 验证
     * @param path
     */
    private void verify(String path) {
        LoopAuthParamException
                .isNotEmpty(
                        path,
                        LoopAuthExceptionEnum.PARAM_IS_NULL,
                        "prestrainPath is null or empty"
                );
    }

}
