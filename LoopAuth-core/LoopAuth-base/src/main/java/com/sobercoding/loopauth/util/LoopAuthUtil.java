package com.sobercoding.loopauth.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

}
