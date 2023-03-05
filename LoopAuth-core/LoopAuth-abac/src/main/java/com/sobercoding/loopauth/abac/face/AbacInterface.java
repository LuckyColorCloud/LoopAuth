package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.annotation.CheckAbacAnnotation;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.abac.policy.model.Policy;
import com.sobercoding.loopauth.abac.policy.model.PropertyEnum;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.abac.annotation.CheckAbac;

import java.util.*;
import java.util.function.Supplier;

/**
 * abac获取规则
 *
 * @author Sober
 */
public interface AbacInterface<A, C, R, S> {


    /**
     * 获取一个或多个路由/权限代码所属的 规则
     *
     * @param route            路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode);


    /**
     * 获取规则构造
     * @return
     */
    PolicyWrapper<A, C, R, S> getPolicyWrapper();

    /**
     * 获取操作属性
     * @return
     */
    A getAction();

    /**
     * 获取环境属性
     * @return
     */
    C getContextual();

    /**
     * 获取主题属性
     * @return
     */
    S getSubject();

    /**
     * 获取访问对象属性
     * @return
     */
    R getResObject();

    /**
     * 规则匹配
     *
     * @param method 请求方式
     * @param route  路由
     * @author: Sober
     */
    default void check(String route, String method) {
        this.check(this.getPolicySet(route, LoopAuthHttpMode.toEnum(method)));
    }

    /**
     * 规则匹配 注解模式
     *
     * @param checkAbac 注解
     * @author: Sober
     */
    default void check(CheckAbac checkAbac) {
        this.check(CheckAbacAnnotation.getPolicySet(checkAbac));
    }

    default void check(Set<Policy> policySet) {
        Optional<Set<Policy>> optionalPolicies = Optional.ofNullable(policySet);
        // 判空
        optionalPolicies.ifPresent(
                // 迭代所有规则
                policies -> {
                    policies.forEach(item -> {
                        this.checkContextual(item);
                        this.checkResObject(item);
                        this.checkSubject(item);
                        this.checkAction(item);
                    });
                }
        );
    }

    default void checkContextual(Policy policy) {
        // 环境属性校验
        Map<String, String> maps = policy.getPropertyMap().get(PropertyEnum.CONTEXTUAL.toString());
        Optional.ofNullable(maps)
                .ifPresent(item -> {
                    Set<String> keySet = item.keySet();
                    keySet.forEach(key -> {
                        // 执行访问者主题属性验证
                        Optional.ofNullable(this.getPolicyWrapper().getContextualData().getFunctionHashMap().get(key))
                                .ifPresent(valueFunc -> {
                                    Supplier supplier = () -> valueFunc.apply(this.getContextual());
                                    boolean isFlag = this.getPolicyWrapper().getContextualData().getMateFunctionHashMap().get(key)
                                            .mate(supplier, item.get(key));
                                    if (!isFlag) {
                                        throw new LoopAuthPermissionException(
                                                LoopAuthExceptionEnum.NO_PERMISSION_F,
                                                policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                        );
                                    }
                                });
                    });
                });
    }

    default void checkResObject(Policy policy) {
        // 环境属性校验
        Map<String, String> maps = policy.getPropertyMap().get(PropertyEnum.RES_OBJECT.toString());
        Optional.ofNullable(maps)
                .ifPresent(item -> {
                    Set<String> keySet = item.keySet();
                    keySet.forEach(key -> {
                        // 执行访问者主题属性验证
                        Optional.ofNullable(this.getPolicyWrapper().getResObjectData().getFunctionHashMap().get(key))
                                .ifPresent(valueFunc -> {
                                    Supplier supplier = () -> valueFunc.apply(this.getResObject());
                                    boolean isFlag = this.getPolicyWrapper().getResObjectData().getMateFunctionHashMap().get(key)
                                            .mate(supplier, item.get(key));
                                    if (!isFlag) {
                                        throw new LoopAuthPermissionException(
                                                LoopAuthExceptionEnum.NO_PERMISSION_F,
                                                policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                        );
                                    }
                                });
                    });
                });

    }

    default void checkSubject(Policy policy) {
        // 环境属性校验
        Map<String, String> maps = policy.getPropertyMap().get(PropertyEnum.SUBJECT.toString());
        Optional.ofNullable(maps)
                .ifPresent(item -> {
                    Set<String> keySet = item.keySet();
                    keySet.forEach(key -> {
                        // 执行访问者主题属性验证
                        Optional.ofNullable(this.getPolicyWrapper().getSubjectData().getFunctionHashMap().get(key))
                                .ifPresent(valueFunc -> {
                                    Supplier supplier = () -> valueFunc.apply(this.getSubject());
                                    boolean isFlag = this.getPolicyWrapper().getSubjectData().getMateFunctionHashMap().get(key)
                                            .mate(supplier, item.get(key));
                                    if (!isFlag) {
                                        throw new LoopAuthPermissionException(
                                                LoopAuthExceptionEnum.NO_PERMISSION_F,
                                                policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                        );
                                    }
                                });
                    });
                });

    }

    default void checkAction(Policy policy) {
        // 环境属性校验
        Map<String, String> maps = policy.getPropertyMap().get(PropertyEnum.ACTION.toString());
        Optional.ofNullable(maps)
                .ifPresent(item -> {
                    Set<String> keySet = item.keySet();
                    keySet.forEach(key -> {
                        // 执行访问者主题属性验证
                        Optional.ofNullable(this.getPolicyWrapper().getActionData().getFunctionHashMap().get(key))
                                .ifPresent(valueFunc -> {
                                    Supplier supplier = () -> valueFunc.apply(this.getAction());
                                    boolean isFlag = this.getPolicyWrapper().getActionData().getMateFunctionHashMap().get(key)
                                            .mate(supplier, item.get(key));
                                    if (!isFlag) {
                                        throw new LoopAuthPermissionException(
                                                LoopAuthExceptionEnum.NO_PERMISSION_F,
                                                policy.getName() + "规则中" + "属性" + "'" + key + "'校验失败!"
                                        );
                                    }
                                });
                    });
                });
    }

}
