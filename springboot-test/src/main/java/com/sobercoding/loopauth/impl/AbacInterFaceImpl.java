package com.sobercoding.loopauth.impl;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.loopauth.abac.policy.model.Action;
import com.sobercoding.loopauth.abac.policy.model.Contextual;
import com.sobercoding.loopauth.abac.policy.model.Subject;
import com.sobercoding.loopauth.model.ActionModel;
import com.sobercoding.loopauth.model.ContextualModel;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.model.UserModel;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sober
 */
@Component
public class AbacInterFaceImpl implements AbacInterface<ActionModel, ContextualModel, Object, UserModel> {

    /**
     * ABAC规则包装
     */
    PolicyWrapper<ActionModel, ContextualModel, Object, UserModel> policyWrapper;

    public void setPolicyWrapper(PolicyWrapper<ActionModel, ContextualModel, Object, UserModel> policyWrapper) {
        this.policyWrapper = policyWrapper;
    }

    public PolicyWrapper<ActionModel, ContextualModel, Object, UserModel> getPolicyWrapper() {
        if (this.policyWrapper == null) {
            synchronized (AbacInterface.class) {
                if (this.policyWrapper == null) {
                    /**
                     * 访问者主题属性 通常为账户属性
                     */
                    Subject<UserModel> subject = Subject
                            .init("loginId",
                                    UserModel::getId,
                                    Object::equals)
                            .structure("userName",
                                    UserModel::getName,
                                    Object::equals);

                    /**
                     * 环境属性 通常为非账户的属性 如时间、ip等
                     */
                    Contextual<ContextualModel> contextual = Contextual
                            .init("time",
                                    ContextualModel::getNowTime,
                                    (v, r) -> {
                                        long time = v.get();
                                        long roletime = Long.parseLong(r);
                                        return roletime > time;
                                    }
                            );

                    /**
                     * 操作类型 通常问请求方式GET 或者操作code等
                     */
                    Action<ActionModel> action = Action
                            .init("model",
                                    ActionModel::getLoopAuthHttpMode,
                                    Object::equals);

                    this.setPolicyWrapper(
                            PolicyWrapper.<ActionModel, ContextualModel, Object, UserModel>builder()
                                    .subject(subject)
                                    .action(action)
                                    .contextual(contextual)
                    );
                }
            }
        }
        return this.policyWrapper;
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
        if (route.equals("/test/abac") && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)){
            set.add(new Policy()
                    // 规则名称
                    .setName("test")
                    // 规则中的属性名称 及 属性值 用于后续进行 规则匹配校验
                    .setProperty("time", "1676126656326")
            );
        }
        return set;
    }

    @Override
    public ActionModel getAction() {
        ActionModel actionModel = new ActionModel();
        actionModel.setLoopAuthHttpMode(LoopAuthHttpMode.GET);
        actionModel.setDoc("测试");
        return actionModel;
    }

    @Override
    public ContextualModel getContextual() {
        ContextualModel contextualModel = new ContextualModel();
        return contextualModel;
    }

    @Override
    public UserModel getSubject() {
        UserModel userModel = new UserModel();
        userModel.setId(LoopAuthSession.getTokenModel().getLoginId());
        userModel.setName("小小用户");
        return userModel;
    }
}