package com.sobercoding.loopauth.abac.policy;

import com.sobercoding.loopauth.function.MaFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 规则验证
 * @author Sober
 */
public class RuleMate<V, R> {

    /**
     * value上下文预处理
     * @param supplier
     * @return
     */
    Supplier<V> supplier;

    /**
     * 最后组装成的规则组
     */
    List<List<MaFunction<V , R>>> maFunctionList;

    /**
     * 载入上下文预处理方法
     * @param supplier
     * @return
     */
    public RuleMate<V, R> value(Supplier<V> supplier) {
        this.supplier = supplier;
        return this;
    }

    /**
     * 相等
     */
    public RuleMate eq() {
        List<MaFunction<V, R>> list = new ArrayList<>();
        list.add(Object::equals);
        maFunctionList.add(list);
        return this;
    }

    /**
     * 相等 模糊匹配
     */
    public RuleMate fuzzyEq() {
        List<MaFunction<V, R>> list = new ArrayList<>();
        list.add(Object::equals);
        maFunctionList.add(list);
        return this;
    }


    /**
     * 闭区间
     */
    public RuleMate between(){
        List<MaFunction<V, R>> list = new ArrayList<>();
        list.add((value, rule) -> {
            String val = (String) value;
            String[] rules = ((String) rule).split("~");
            return val.compareTo(rules[0]) >= 0 && val.compareTo(rules[1]) <= 0;
        });
        maFunctionList.add(list);
        return this;
    }

    /**
     * 包含
     */
    public RuleMate in(){
        List<MaFunction<V, R>> list = new ArrayList<>();
        list.add((value, rule) -> {
            String val = (String) value;
            String[] rules = ((String) rule).split(",");
            return Arrays.asList(rules).contains(val);
        });
        maFunctionList.add(list);
        return this;
    }
}
