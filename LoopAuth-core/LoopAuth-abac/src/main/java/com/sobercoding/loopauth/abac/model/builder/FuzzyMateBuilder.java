package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.function.PolicyFun;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;
import com.sobercoding.loopauth.util.AuthUtil;
import javafx.util.Builder;

import java.util.Set;

/**
 * @author Sober
 */
public class FuzzyMateBuilder implements Builder<PolicyFun> {

    /**
     * 验证模式：AND | OR | NON，默认AND
     *
     * @return 验证模式
     */
    private LoopAuthVerifyMode loopAuthVerifyMode = LoopAuthVerifyMode.AND;

    public FuzzyMateBuilder setLoopAuthVerifyMode(LoopAuthVerifyMode loopAuthVerifyMode) {
        this.loopAuthVerifyMode = loopAuthVerifyMode;
        return this;
    }

    @Override
    public PolicyFun build() {
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            return (value, rule) -> {
                Set<String> values = (Set<String>) value;
                String[] rules = ((String) rule).split(",");
                return AuthUtil.checkAnd(values,rules);
            };
        } else if (loopAuthVerifyMode == LoopAuthVerifyMode.OR) {
            return (value, rule) -> {
                Set<String> values = (Set<String>) value;
                String[] rules = ((String) rule).split(",");
                return AuthUtil.checkOr(values,rules);
            };
        } else {
            return (value, rule) -> {
                Set<String> values = (Set<String>) value;
                String[] rules = ((String) rule).split(",");
                return AuthUtil.checkNon(values,rules);
            };
        }
    }
}
