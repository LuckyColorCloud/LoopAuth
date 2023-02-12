package com.sobercoding.loopauth.abac;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.face.AbacInterfaceImpl;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class AbacStrategy {


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
