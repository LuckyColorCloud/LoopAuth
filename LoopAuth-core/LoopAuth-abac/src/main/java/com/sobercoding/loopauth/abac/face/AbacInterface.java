package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.abac.annotation.CheckAbac;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * abac获取规则
 *
 * @author Sober
 */
public interface AbacInterface<A, C, R, S> extends AbacWrapper<A, C, R, S> {


    /**
     * 获取一个或多个路由/权限代码所属的 规则
     *
     * @param route            路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode);

    /**
     * 规则匹配
     *
     * @param method 请求方式
     * @param route  路由
     * @author: Sober
     */
    default void check(String route, String method) {
        // 获取当前匹配的规则
        Optional<Set<Policy>> policy = Optional.ofNullable(
                this.getPolicySet(route, LoopAuthHttpMode.toEnum(method))
        );
        // 判空
        policy.ifPresent(
                // 迭代所有规则
                policies -> policies.forEach(item -> {
                    // 迭代规则中属性
                    Set<String> keySet = item.getPropertyMap().keySet();
                    keySet.forEach(key -> {
                        // 执行访问者主题属性验证
                        Optional.ofNullable(this.getPolicyWrapper().getContextualData().getFunctionHashMap().get(key))
                                .ifPresent(valueFunc -> {
                                    Supplier supplier = () -> valueFunc.apply(this.getContextual());
                                    boolean isFlag = this.getPolicyWrapper().getContextualData().getMateFunctionHashMap().get(key)
                                            .mate(supplier, (String) item.getPropertyMap().get(key));
                                    if (!isFlag) {
                                        throw new LoopAuthPermissionException(
                                                LoopAuthExceptionEnum.NO_PERMISSION_F,
                                                item.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                        );
                                    }
                                });
                    });
                })
        );
    }

    /**
     * 规则匹配 注解模式
     *
     * @param checkAbac 注解
     * @author: Sober
     */
    default void check(CheckAbac checkAbac) {
        Policy policy = new Policy();
        policy.setName(checkAbac.name());
        Arrays.stream(checkAbac.value()).forEach(abacProperty ->
                policy.setProperty(abacProperty.name(), abacProperty.value()));
        Set<String> keySet = policy.getPropertyMap().keySet();
        keySet.forEach(key -> {
            // 执行访问者主题属性验证
            Optional.ofNullable(this.getPolicyWrapper().getContextualData().getFunctionHashMap().get(key))
                    .ifPresent(valueFunc -> {
                        Supplier supplier = () -> valueFunc.apply(this.getContextual());
                        boolean isFlag = this.getPolicyWrapper().getContextualData().getMateFunctionHashMap().get(key)
                                .mate(supplier, (String) policy.getPropertyMap().get(key));
                        if (!isFlag) {
                            throw new LoopAuthPermissionException(
                                    LoopAuthExceptionEnum.NO_PERMISSION_F,
                                    policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                            );
                        }
                    });
        });
    }

}
