package com.sobercoding.loopauth.auth.model;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import com.sobercoding.loopauth.abac.policy.model.authProperty.IntervalType;
import com.sobercoding.loopauth.function.VerifyFunction;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Sober
 */
@PropertyPretrain
@Component
public class ContextualModel {


    public Long nowTime() {
        return IntervalType.NONE.creation();
    }

    /**
     * 字符串校验
     */
    public final static Supplier<String> nowTime = () -> String.valueOf(IntervalType.NONE.creation());

}
