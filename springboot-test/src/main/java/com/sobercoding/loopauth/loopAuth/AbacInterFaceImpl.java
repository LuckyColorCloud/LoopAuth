package com.sobercoding.loopauth.loopAuth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AbacInterFaceImpl implements AbacInterface {

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route) {
        Set<Policy> set = new HashSet<>();
        if (route.equals("/test/abac1")){
            set.add(new Policy()
                    .setName("test")
                    .setApiGroup("/abac1")
                    .setLoopAuthHttpMode(LoopAuthHttpMode.ALL)
                    .setPermissionGroup("abac*")
                    .setProperty("loginId", "1"));
        }
        return set;
    }
}
