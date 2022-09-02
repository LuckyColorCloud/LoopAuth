package com.sobercoding.loopauth.model.builder;

import com.sobercoding.loopauth.function.PolicyFun;
import com.sobercoding.loopauth.model.authProperty.TimeInterval;
import com.sobercoding.loopauth.util.AuthUtil;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import javafx.util.Builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Sober
 */
public class AbacPolicyFunBuilder implements Builder<Map<String, PolicyFun>> {

    private Map<String, PolicyFun> policyFunMap = new HashMap<>();

    public AbacPolicyFunBuilder loginId(String key) {
        policyFunMap.put(key,(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            return Arrays.asList(loginIdRules).contains(loginId);
        });
        return this;
    }

    public AbacPolicyFunBuilder loginIdNot(String key) {
        policyFunMap.put(key,(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            return Arrays.stream(loginIdRules).noneMatch(item -> item.equals(loginId));
        });
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
