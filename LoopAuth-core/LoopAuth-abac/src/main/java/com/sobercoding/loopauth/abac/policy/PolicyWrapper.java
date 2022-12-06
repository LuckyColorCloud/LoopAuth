package com.sobercoding.loopauth.abac.policy;

import com.sobercoding.loopauth.abac.policy.interfaces.ActionFace;
import com.sobercoding.loopauth.abac.policy.interfaces.ContextualFace;
import com.sobercoding.loopauth.abac.policy.interfaces.ResObjectFace;
import com.sobercoding.loopauth.abac.policy.interfaces.SubjectFace;

/**
 * ABAC 规则包装
 * @param <Subject> 访问者的属性
 * @param <Action> 操作类型
 * @param <ResObject> 资源对象
 * @param <Contextual> 环境属性
 * @author sober
 */
public class PolicyWrapper<Subject, Action, ResObject, Contextual>
        implements
        SubjectFace<Subject>,
        ActionFace<Action>,
        ResObjectFace<ResObject>,
        ContextualFace<Contextual> {



}
