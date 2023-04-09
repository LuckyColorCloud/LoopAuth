package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthParamException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
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
        // 不为空
        LoopAuthParamException
                .isNotEmpty(
                        path,
                        LoopAuthExceptionEnum.PARAM_IS_NULL,
                        "prestrainPath is null or empty"
                );
        // 包路径 class路径区分处理
        return path.charAt(path.length() - 1) != '*' ?
                perseByCalss(path) :
                perseByPackage(path);
    }

    /**
     * 处理包路径获得所有符合的class路径
     * @param path
     * @return
     */
    private Set<String> perseByPackage(String path) {
        // 存储类路径
        Set<String> paths = new HashSet<>();
        // 去除.* 替换.为/
        path = path.substring(0, path.lastIndexOf(".")).replace(".", "/");
        try {
            // 获取路径下所有条目
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources(path);
            while (resources.hasMoreElements()) {
                // 获取当前条目
                URL url = resources.nextElement();
                // 获取所有文件
                String[] filePaths = Optional.ofNullable(new File(url.getFile()).list())
                        .orElse(new String[0]);
                for (String clazzPath : filePaths) {
                    this.paths.addAll(perseByCalss(path + "/" + clazzPath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO 异常需要处理
            throw new RuntimeException(e);
        }
        return paths;
    }



    /**
     * 处理class路径
     * @param path
     * @return
     */
    private Set<String> perseByCalss(String path) {
        URL url = ClassLoader.getSystemResource(path);
        System.out.println(path);
        // 验证类路径正确性
        return url != null ?
                new HashSet<String>(){{this.add(path.replaceAll("\\.class", "").replace("/", "."));}} :
                new HashSet<String>();
    }

}
