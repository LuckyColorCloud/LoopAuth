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
        Policy policy = new Policy();
        policy.setName(checkAbac.name());
        Arrays.stream(checkAbac.value()).forEach(abacProperty ->
                policy.setProperty(abacProperty.name(),abacProperty.value()));
        Set<String> keySet = policy.getPropertyMap().keySet();
        keySet.forEach(key ->
                Optional.ofNullable(AbacStrategy.abacPoAndSuMap.get(key))
                    .ifPresent(abacPoAndSu -> {
                        if (!abacPoAndSu.getMaFunction()
                                .mate(abacPoAndSu.getSupplierMap().get(), policy.getPropertyMap().get(key))){
                            throw new LoopAuthPermissionException(
                                    LoopAuthExceptionEnum.NO_PERMISSION_F,
                                    policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                    );
                        }
                    }));
    }

    /**
     * 规则匹配
     * @author: Sober
     * @param method 请求方式
     * @param route 路由
     */
    @Deprecated
    public static void check(String route, String method) {
        Optional<Set<Policy>> policy = Optional.ofNullable(AbacStrategy.getAbacInterface().getPolicySet(route, LoopAuthHttpMode.toEnum(method)));
        policy.ifPresent(policies -> policies.forEach(item -> {
            Set<String> keySet = item.getPropertyMap().keySet();
            keySet.forEach(key -> {
                Optional.ofNullable(AbacStrategy.abacPoAndSuMap
                                .get(key))
                        .ifPresent(abacPoAndSu -> {
                            if (!abacPoAndSu.getMaFunction()
                                    .mate(abacPoAndSu.getSupplierMap().get(), item.getPropertyMap().get(key))){
                                throw new LoopAuthPermissionException(
                                        LoopAuthExceptionEnum.NO_PERMISSION_F,
                                        item.getName() + "规则中" + "属性" + "\"" + key + "\"校验失败!"
                                );
                            }
                        });
            });
        }));
    }

    /**
     * 规则匹配
     * @author: Sober
     * @param method 请求方式
     * @param route 路由
     */
    public static void check1(String route, String method) {
        AbacStrategy.getAbacInterface().check(route,method);
    }
}
