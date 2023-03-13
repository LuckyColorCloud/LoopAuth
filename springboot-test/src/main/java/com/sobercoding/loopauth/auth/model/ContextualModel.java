package com.sobercoding.loopauth.auth.model;

import com.sobercoding.loopauth.abac.policy.model.authProperty.IntervalType;
import org.springframework.stereotype.Component;

/**
 * @author Sober
 */
@Component
public class ContextualModel {

    private Long nowTime;

    public Long getNowTime() {
        return IntervalType.NONE.creation();
    }

}
