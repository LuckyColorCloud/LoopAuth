package com.sobercoding.loopauth.abac;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.face.AbacInterfaceImpl;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.function.MaFunction;

import java.util.*;
import java.util.function.Supplier;

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
     * ABAC权限认证 Bean 获取一个或多个路由/权限代码所属的 规则
     */
    private volatile static AbacInterface abacInterface;

    public static void setAbacInterface(AbacInterface abacInterface) {
        AbacStrategy.abacInterface = abacInterface;
    }

    public static AbacInterface getAbacInterface() {
        if (abacInterface == null) {
            synchronized (AbacStrategy.class) {
                if (abacInterface == null) {
                    setAbacInterface(new AbacInterfaceImpl());
                }
            }
        }
        return abacInterface;
    }

}
