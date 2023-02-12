package com.sobercoding.loopauth.abac.policy;


import com.sobercoding.loopauth.abac.policy.model.Action;
import com.sobercoding.loopauth.abac.policy.model.Contextual;
import com.sobercoding.loopauth.abac.policy.model.ResObject;
import com.sobercoding.loopauth.abac.policy.model.Subject;

/**
 * 包装类
 * ABAC 规则包装
 * @author sober
 */

public class PolicyWrapper<A,C,R,S> {
    private Action<A> actionData;

    private Contextual<C> contextualData;

    private ResObject<R> resObjectData;

    private Subject<S> subjectData;

    private PolicyWrapper() {

    }

    public static <A,C,R,S> PolicyWrapper<A,C,R,S> builder() {
        return new PolicyWrapper<A,C,R,S>();
    }


    public PolicyWrapper<A,C,R,S> subject(Subject<S> subjectData){
        this.subjectData = subjectData;
        return this;
    }
    public PolicyWrapper<A,C,R,S> action(Action<A> actionData){
        this.actionData = actionData;
        return this;
    }
    public PolicyWrapper<A,C,R,S> contextual(Contextual<C> contextualData){
        this.contextualData = contextualData;
        return this;
    }
    public PolicyWrapper<A,C,R,S> resObject(ResObject<R> resObjectData){
        this.resObjectData = resObjectData;
        return this;
    }



    public Action<A> getActionData() {
        return actionData;
    }

    public Contextual<C> getContextualData() {
        return contextualData;
    }

    public ResObject<R> getResObjectData() {
        return resObjectData;
    }

    public Subject<S> getSubjectData() {
        return subjectData;
    }

}
