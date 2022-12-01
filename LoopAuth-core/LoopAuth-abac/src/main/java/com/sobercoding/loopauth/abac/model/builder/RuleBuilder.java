package com.sobercoding.loopauth.abac.model.builder;

import com.sobercoding.loopauth.function.Builder;

/**
 * 规则验证 生成器
 * @author Sober
 */
public class RuleBuilder {

    /**
     * 访问者属性值
     */
    private String value;
    /**
     * 可访问属性值
     */
    private String rule;

    /**
     * 载入初始值
     * @param value 访问者属性值
     * @param rule 可访问属性值
     * @return
     */
    public RuleBuilder of(String value, String rule){
        this.value = value;
        this.rule = rule;
        return this;
    }


    public void charMate(){

    }
}
