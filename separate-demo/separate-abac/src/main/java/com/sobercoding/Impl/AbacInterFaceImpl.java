package com.sobercoding.Impl;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sober
 */
public class AbacInterFaceImpl implements AbacInterface {

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        // 这里只做演示，自行编写的时候，请根据自己存储abac规则的方式查询获取
        Set<Policy> set = new HashSet<>();
        // 根据路由地址及请求方式查询 插入
        if (route.equals("/test/abac") && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
            set.add(new Policy()
                    // 规则名称
                    .setName("test")
                    // 规则中的属性名称 及 属性值 用于后续进行 规则匹配校验
                    .setProperty("loginId", "1")
            );
        }
        return set;
    }
}
