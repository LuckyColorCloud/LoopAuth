package com.sobercoding.loopauth.util;

import com.sobercoding.loopauth.model.LoopAuthHttpMode;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Yun
 */
public class LoopAuthUtil {

    /**
     * 不可创建工具类
     */
    private LoopAuthUtil() {};
    /**
     * 判断是否为空对象 或 字符串
     *
     * @param object 对象
     * @return 是否
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Optional) {
            return !((Optional<?>)object).isPresent();
        } else if (object instanceof CharSequence) {
            return ((CharSequence)object).length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection<?>)object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        }else {
            return false;
        }
    }

    /**
     * 判断是否不为空对象 或 字符串
     *
     * @param object 对象
     * @return 是否
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 匹配元素是否存在
     */
    public static boolean hasElement(Set<String> roleSet, String role) {
        // 空集合直接返回false
        if (LoopAuthUtil.isEmpty(roleSet)) {
            return false;
        }
        // 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配
        if (roleSet.contains(role)) {
            return true;
        }
        // 开始模糊匹配
        for (String path : roleSet) {
            if (LoopAuthUtil.fuzzyMatching(path, role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 模糊匹配
     * @param meCompetence 自己拥有权限
     * @param competence   需要匹配的权限
     * @return 是否
     */
    public static boolean fuzzyMatching(String meCompetence,String competence) {
        if (isEmpty(meCompetence) || isEmpty(competence)){
            return false;
        }
        if (isEmpty(meCompetence) && isEmpty(competence)){
            return false;
        }
        if (!meCompetence.contains("*")){
            return meCompetence.equalsIgnoreCase(competence);
        }

        return Pattern.matches(meCompetence.replaceAll("\\*", ".*"), competence);
    }
    /**
     * 路由模糊匹配
     * @author Sober
     * @param excludeList 放行的路由
     * @param request 请求体
     * @param includeList 拦截的路由
     * @return boolean
     */
    private static boolean matchPaths(ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> excludeList, ServletRequest request, ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> includeList) {
        // 是否存在匹配到的路由
        boolean isInclude;
        if (request == null || includeList.isEmpty()) {
            // 没有需要拦截的路由直接放行
            return false;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 当前路由
        String path = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        // 当前请求方式
        LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(method);

        // 列表化需要放行的路由
        ConcurrentHashMap.KeySetView<String, HashSet<LoopAuthHttpMode>> excludes = excludeList.keySet();
        // 匹配路由 和 请求方法 存在一致则直接放行
        isInclude = excludes.stream().anyMatch(item ->
                LoopAuthUtil.fuzzyMatching(item, path) &&
                        (excludeList.get(item).contains(LoopAuthHttpMode.ALL) ||
                                excludeList.get(item).contains(loopAuthHttpMode)));
        if (isInclude) {
            return false;
        }

        // 列表化需要拦截的路由
        ConcurrentHashMap.KeySetView<String, HashSet<LoopAuthHttpMode>> includes = includeList.keySet();
        // 匹配路由 和 请求方法 存在一致则直接拦截
        isInclude = includes.stream().anyMatch(item ->
                LoopAuthUtil.fuzzyMatching(item, path) &&
                        (includeList.get(item).contains(LoopAuthHttpMode.ALL) ||
                                includeList.get(item).contains(loopAuthHttpMode)));
        return isInclude;
    }

}
