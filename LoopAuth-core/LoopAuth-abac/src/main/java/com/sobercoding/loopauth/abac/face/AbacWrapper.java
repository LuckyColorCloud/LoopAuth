package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.policy.PolicyWrapper;

public interface AbacWrapper<A, C, R, S> {

    /**
     * 获取规则构造
     * @return
     */
    PolicyWrapper<A, C, R, S> getPolicyWrapper();

    /**
     * 获取操作属性
     * @return
     */
    A getAction();

    /**
     * 获取环境属性
     * @return
     */
    C getContextual();

    /**
     * 获取主题属性
     * @return
     */
    S getSubject();

    /**
     * 获取访问对象属性
     * @return
     */
    R getResObject();

}
