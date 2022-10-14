package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.function.MaFunction;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import javafx.util.Builder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 组装abac规则模型
 * @author Sober
 */
public class AbacPolicyFunBuilder implements Builder<Map<String, MaFunction>> {

    private Map<String, MaFunction> MaFunctionMap = new HashMap<>();

    public AbacPolicyFunBuilder loginId() {
        MaFunctionMap.put("loginId",(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            if (!Arrays.asList(loginIdRules).contains(loginId)) {
                throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
            }
            return Arrays.asList(loginIdRules).contains(loginId);
        });
        return this;
    }

    public AbacPolicyFunBuilder loginIdNot() {
        MaFunctionMap.put("loginIdNON",(value, rule) -> {
            String loginId = (String) value;
            String[] loginIdRules = ((String) rule).split(",");
            return Arrays.stream(loginIdRules).noneMatch(item -> item.equals(loginId));
        });
        return this;
    }

    public AbacPolicyFunBuilder role(LoopAuthVerifyMode loopAuthVerifyMode) {
        MaFunctionMap.put("role" + loopAuthVerifyMode.name(), new FuzzyMateBuilder()
                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                .build());
        return this;
    }

    public AbacPolicyFunBuilder permission(LoopAuthVerifyMode loopAuthVerifyMode) {
        MaFunctionMap.put("permission" + loopAuthVerifyMode.name(), new FuzzyMateBuilder()
                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                .build());
        return this;
    }


    public AbacPolicyFunBuilder setPolicyFun(String key, MaFunction policyFun) {
        MaFunctionMap.put(key, policyFun);
        return this;
    }

    @Override
    public Map<String, MaFunction> build() {
        return MaFunctionMap;
    }

}
