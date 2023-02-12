package com.sobercoding.loopauth.abac.carryout;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.annotation.CheckAbac;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

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
