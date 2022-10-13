package com.sobercoding.loopauth.loopAuth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AbacInterFaceImpl implements AbacInterface {

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param permission 权限代码
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, String permission) {
        Set<Policy> set = new HashSet<>();
        set.add(new Policy()
                .setName("test")
                .setApiGroup("/abac1")
                .setPermissionGroup("abac*")
                .setProperty("loginId", 1));
        return set;
    }
}
