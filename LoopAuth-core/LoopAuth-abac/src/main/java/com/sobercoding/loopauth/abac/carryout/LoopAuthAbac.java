package com.sobercoding.loopauth.abac.carryout;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;

import java.util.Optional;
import java.util.Set;

/**
 * @author Sober
 */
public class LoopAuthAbac {

    /**
     * 规则匹配
     * @author: Sober
     * @param method 请求方式
     * @param route 路由
     */
    public static void check(String route, String method) {
        Optional<Set<Policy>> policy = Optional.ofNullable(AbacStrategy.getAbacInterface().getPolicySet(route, LoopAuthHttpMode.toEnum(method)));
        policy.ifPresent(policies -> policies.forEach(item -> {
            Set<String> keySet = item.getPropertyMap().keySet();
            keySet.forEach(key -> {
                Optional.ofNullable(AbacStrategy.abacPoAndSuMap
                                .get(key))
                        .ifPresent(abacPoAndSu -> {
                            abacPoAndSu.getMaFunction()
                                    .mate(abacPoAndSu.getSupplierMap().get(), item.getPropertyMap().get(key));
                        });
            });
        }));
    }

}
