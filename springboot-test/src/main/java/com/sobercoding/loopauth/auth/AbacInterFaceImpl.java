package com.sobercoding.loopauth.auth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.face.AbacWrapper;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.abac.policy.model.Policy;
import com.sobercoding.loopauth.abac.policy.model.Subject;
import com.sobercoding.loopauth.auth.model.ActionModel;
import com.sobercoding.loopauth.auth.model.ContextualModel;
import com.sobercoding.loopauth.auth.model.UserModel;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sober
 */
@Component
public class AbacInterFaceImpl
        extends AbacWrapper<ActionModel, ContextualModel, Object, UserModel>
        implements AbacInterface<ActionModel, ContextualModel, Object, UserModel> {
    /**
     * 注入
     */
    AbacInterFaceImpl(UserModel userModel) {

        this.subject = userModel;

        // 访问者主题属性 通常为账户属性
        Subject<UserModel> subject = Subject
                // id规则初始化
                .init("loginId",
                        UserModel::getId,
                        (v, r) -> v.get().equals(r));

        // 载入规则
        policyWrapper = PolicyWrapper.<ActionModel, ContextualModel, Object, UserModel>builder()
                .subject(subject);
    }


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
        if ("/loginId".equals(route) && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
        }
        return set;
    }


}