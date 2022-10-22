package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.exception.LoopAuthPermissionException;
import com.sobercoding.loopauth.function.Builder;
import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 组装abac规则模型
 * @author Sober
 */
public class AbacPolicyFunBuilder implements Builder<Map<String, AbacPoAndSu>> {

    /**
     * 规则匹配
     */
    private Map<String, AbacPoAndSu> abacPoAndSuMap = new HashMap<>();

    public AbacPolicyFunBuilder loginId() {
        abacPoAndSuMap.put("loginId", new AbacPoAndSu()
                .setMaFunction((value, rule) -> {
                    String loginId = (String) value;
                    String[] loginIdRules = ((String) rule).split(",");
                    if (!Arrays.asList(loginIdRules).contains(loginId)) {
                        throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
                    }
                })
        );
        return this;
    }

    public AbacPolicyFunBuilder setLoginId(Supplier supplier) {
        abacPoAndSuMap.get("loginId").setSupplierMap(supplier);
        return this;
    }

    public AbacPolicyFunBuilder loginIdNot() {
        abacPoAndSuMap.put("loginIdNON", new AbacPoAndSu()
                .setMaFunction((value, rule) -> {
                    String loginId = (String) value;
                    String[] loginIdRules = ((String) rule).split(",");
                    if (!Arrays.stream(loginIdRules).noneMatch(item -> item.equals(loginId))) {
                        throw new LoopAuthPermissionException(LoopAuthExceptionEnum.NO_PERMISSION);
                    }
                })
        );
        return this;
    }

    public AbacPolicyFunBuilder setLoginIdNot(Supplier supplier) {
        abacPoAndSuMap.get("loginIdNON").setSupplierMap(supplier);
        return this;
    }

    public AbacPolicyFunBuilder role(LoopAuthVerifyMode loopAuthVerifyMode) {
        abacPoAndSuMap.put("role" + loopAuthVerifyMode.name(), new AbacPoAndSu()
                .setMaFunction(
                        new FuzzyMateBuilder()
                                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                                .build()
                )
        );
        return this;
    }

    public AbacPolicyFunBuilder setRole(LoopAuthVerifyMode loopAuthVerifyMode, Supplier supplier) {
        abacPoAndSuMap.get("role" + loopAuthVerifyMode.name()).setSupplierMap(supplier);
        return this;
    }

    public AbacPolicyFunBuilder permission(LoopAuthVerifyMode loopAuthVerifyMode) {
        abacPoAndSuMap.put("permission" + loopAuthVerifyMode.name(), new AbacPoAndSu()
                .setMaFunction(
                        new FuzzyMateBuilder()
                                .setLoopAuthVerifyMode(loopAuthVerifyMode)
                                .build()
                )
        );
        return this;
    }

    public AbacPolicyFunBuilder setPermission(LoopAuthVerifyMode loopAuthVerifyMode, Supplier supplier) {
        abacPoAndSuMap.get("permission" + loopAuthVerifyMode.name()).setSupplierMap(supplier);
        return this;
    }


    public AbacPolicyFunBuilder setPolicyFun(String key, AbacPoAndSu abacPoAndSu) {
        abacPoAndSuMap.put(key, abacPoAndSu);
        return this;
    }

    @Override
    public Map<String, AbacPoAndSu> build() {
        return abacPoAndSuMap;
    }

}
