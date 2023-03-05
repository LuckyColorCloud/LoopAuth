package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.policy.PolicyWrapper;

public abstract class AbacWrapper<A, C, R, S> {

    /**
     * 存储四大属性模型
     */
    private A action;

    private C contextual;

    private S subject;

    private R resObject;

    /**
     * ABAC规则包装
     */
    private PolicyWrapper<A, C, R, S> policyWrapper;


    /**
     * 获取规则构造
     * @return
     */
    PolicyWrapper<A, C, R, S> getPolicyWrapper() {
        return policyWrapper;
    }

    /**
     * 获取操作属性
     * @return
     */
    public A getAction() {
        return action;
    }

    /**
     * 获取环境属性
     * @return
     */
    public C getContextual() {
        return contextual;
    }

    /**
     * 获取主题属性
     * @return
     */
    public S getSubject() {
        return subject;
    }

    /**
     * 获取访问对象属性
     * @return
     */
    public R getResObject() {
        return resObject;
    }

}
