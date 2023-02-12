package com.sobercoding.loopauth.abac.carryout;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.annotation.CheckAbac;

/**
 * @author Sober
 */
public class LoopAuthAbac {

    /**
     * 规则匹配 注解模式
     * @author: Sober
     * @param checkAbac 注解
     */
    public static void check(CheckAbac checkAbac) {
        AbacStrategy.getAbacInterface().check(checkAbac);
    }

    /**
     * 规则匹配
     * @author: Sober
     * @param method 请求方式
     * @param route 路由
     */
    public static void check(String route, String method) {
        AbacStrategy.getAbacInterface().check(route,method);
    }
}
