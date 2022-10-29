package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.function.Builder;
import com.sobercoding.loopauth.function.MaFunction;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.util.AuthUtil;

import java.util.Set;

/**
 * 字符串匹配
 * @author Sober
 */
public class FuzzyMateBuilder implements Builder<MaFunction> {

    /**
     * 验证模式：AND | OR | NON，默认AND
     *
     * @return 验证模式
     */
    private LoopAuthVerifyMode loopAuthVerifyMode = LoopAuthVerifyMode.AND;

    /**
     * TODO
     * 需要可选是否模糊匹配
     */

    public FuzzyMateBuilder setLoopAuthVerifyMode(LoopAuthVerifyMode loopAuthVerifyMode) {
        this.loopAuthVerifyMode = loopAuthVerifyMode;
        return this;
    }

    @Override
    public MaFunction build() {
        if (loopAuthVerifyMode == LoopAuthVerifyMode.AND) {
            return (value, rule) -> {
                Set<String> values = (Set<String>) value;
                String[] rules = ((String) rule).split(",");
                return AuthUtil.checkAnd(values, rules);
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
