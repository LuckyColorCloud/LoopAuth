package com.sobercoding.loopauth.model;

import com.sobercoding.loopauth.abac.policy.model.authProperty.IntervalType;

/**
 * @author Sober
 */
public class ContextualModel {

    private Long nowTime;

    public Long getNowTime() {
        return IntervalType.NONE.creation();
    }

    public void setNowTime(Long nowTime) {
        this.nowTime = nowTime;
    }
}
