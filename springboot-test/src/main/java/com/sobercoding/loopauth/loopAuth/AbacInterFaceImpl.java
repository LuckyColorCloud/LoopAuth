package com.sobercoding.loopauth.loopAuth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AbacInterFaceImpl implements AbacInterface {

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        Set<Policy> set = new HashSet<>();
        if (route.equals("/test/abac1") && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
            set.add(new Policy()
                    // 规则名称
                    .setName("test")
                    // 规则中的属性名称 及 属性值
                    .setProperty("loginId", "2")
            );
        }
        return set;
    }
}
