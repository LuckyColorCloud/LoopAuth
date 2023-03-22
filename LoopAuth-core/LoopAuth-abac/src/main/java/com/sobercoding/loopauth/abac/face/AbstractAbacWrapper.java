package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.policy.PolicyWrapper;

/**
 * @author Yun
 */
public abstract class AbstractAbacWrapper<A, C, R, S> {

    /**
     * 存储四大属性模型
     */
    protected A action;

    protected C contextual;

    protected S subject;

    protected R resObject;

    /**
     * ABAC规则包装
     */
    protected PolicyWrapper<A, C, R, S> policyWrapper;


    /**
     * 获取规则构造
     * @return
     */
    public PolicyWrapper<A, C, R, S> getPolicyWrapper() {
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
