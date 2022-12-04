package com.sobercoding.loopauth.abac.rule;

import com.sobercoding.loopauth.function.MaFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 规则验证
 * @author Sober
 */
public class RuleMate {
    /**
     *  ABAC鉴权获取属性值方式
     */
    Supplier<Object> supplierMap;

    /**
     * 最后组装成的规则组
     */
    List<List<MaFunction<Object , Object>>> maFunctionList;

    /**
     * 是否模糊匹配
     */
    private Boolean isFuzzy = false;


    /**
     * 相等
     */
    public RuleMate eq() {
        List<MaFunction<Object, Object>> list = new ArrayList<>();
        list.add(Object::equals);
        maFunctionList.add(list);
        return this;
    }


    /**
     * 闭区间
     */
    public RuleMate between(){
        List<MaFunction<Object, Object>> list = new ArrayList<>();
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
        List<MaFunction<Object, Object>> list = new ArrayList<>();
        list.add((value, rule) -> {
            String val = (String) value;
            String[] rules = ((String) rule).split(",");
            return Arrays.asList(rules).contains(val);
        });
        maFunctionList.add(list);
        return this;
    }

    /**
     * 开启模糊查询
     */
    public RuleMate fuzzy() {
        this.isFuzzy = true;
        return this;
    }
}
