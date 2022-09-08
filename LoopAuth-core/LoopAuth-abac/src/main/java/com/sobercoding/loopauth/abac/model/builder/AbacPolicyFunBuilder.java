package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.function.PolicyFun;
import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;
import javafx.util.Builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sober
 */
public class AbacPolicyFunBuilder implements Builder<Map<String, PolicyFun>> {

    private Map<String, PolicyFun> policyFunMap = new HashMap<>();

    public AbacPolicyFunBuilder loginId() {
        policyFunMap.put("loginId",(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            return Arrays.asList(loginIdRules).contains(loginId);
        });
        return this;
    }

    public AbacPolicyFunBuilder loginIdNot() {
        policyFunMap.put("loginIdNON",(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            return Arrays.stream(loginIdRules).noneMatch(item -> item.equals(loginId));
        });
        return this;
    }

    public AbacPolicyFunBuilder role(LoopAuthVerifyMode loopAuthVerifyMode) {
        policyFunMap.put("role" + loopAuthVerifyMode.name(), new FuzzyMateBuilder()
                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                .build());
        return this;
    }

    public AbacPolicyFunBuilder permission(LoopAuthVerifyMode loopAuthVerifyMode) {
        policyFunMap.put("permission" + loopAuthVerifyMode.name(), new FuzzyMateBuilder()
                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                .build());
        return this;
    }


    public AbacPolicyFunBuilder setPolicyFun(String key, PolicyFun policyFun) {
        policyFunMap.put(key, policyFun);
        return this;
    }

    @Override
    public Map<String, PolicyFun> build() {
        return policyFunMap;
    }

}
