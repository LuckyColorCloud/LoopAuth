package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.model.Policy;

import java.util.Set;

/**
 * abac获取规则
 * @author Sober
 */

public interface AbacInterface {
    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param permission 权限代码
     * @return 去重后的集合
     */
    Set<Policy> getPolicySet(String route, String permission);
}
