package com.sobercoding.loopauth.abac;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.face.AbacInterfaceImpl;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.abac.policy.RuleMate;

import java.util.*;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class AbacStrategy {

    /**
     * ABAC鉴权匹配及获取属性值方式
     */
    public volatile static Map<String, AbacPoAndSu> abacPoAndSuMap = new HashMap<>();

    /**
     * ABAC规则包装
     */
    public volatile static Map<String, RuleMate> ruleMateMap = new HashMap<>();

    /**
     * ABAC规则包装
     */
    private volatile static PolicyWrapper<?, ?, ?, ?> policyWrapper;

    public static void setPolicyWrapper(PolicyWrapper<?, ?, ?, ?> policyWrapper) {
        AbacStrategy.policyWrapper = policyWrapper;
    }

    public static PolicyWrapper<?, ?, ?, ?> getPolicyWrapper() {
        if (AbacStrategy.policyWrapper == null) {
            synchronized (AbacStrategy.class) {
                if (AbacStrategy.policyWrapper == null) {
                    AbacStrategy.setPolicyWrapper(null);
                }
            }
        }
        return AbacStrategy.policyWrapper;
    }

    /**
     * ABAC权限认证 Bean 获取一个或多个路由/权限代码所属的 规则
     */
    private volatile static AbacInterface<?, ?, ?, ?> abacInterface;

    public static void setAbacInterface(AbacInterface<?, ?, ?, ?> abacInterface) {
        AbacStrategy.abacInterface = abacInterface;
    }

    public static AbacInterface<?, ?, ?, ?> getAbacInterface() {
        if (AbacStrategy.abacInterface == null) {
            synchronized (AbacStrategy.class) {
                if (AbacStrategy.abacInterface == null) {
                    AbacStrategy.setAbacInterface(new AbacInterfaceImpl());
                }
            }
        }
        return AbacStrategy.abacInterface;
    }

}
