package com.sobercoding.loopauth.util;

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
        return object == null || "".equals(object) || object.toString().length() == 0;
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
