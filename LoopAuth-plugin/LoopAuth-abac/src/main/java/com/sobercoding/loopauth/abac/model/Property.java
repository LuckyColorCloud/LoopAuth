package com.sobercoding.loopauth.abac.model;

import com.sobercoding.loopauth.function.PolicyFun;

/**
 * 属性模型，所有属性匹配方式通过此存储
 * @author Sober
 */
public class Property {

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private Object property;

    /**
     * 属性规则
     */
    private Object propertyRule;

    /**
     * 策略执行方法
     */
    private PolicyFun policyFun;

    /**
     * 判断类型 默认true  true即属性规则通过则可访问  false即不通过可访问
     */
    private boolean judge = true;


    public Object getPropertyRule() {
        return propertyRule;
    }

    public Property setPropertyRule(Object propertyRule) {
        this.propertyRule = propertyRule;
        return this;
    }

    public String getName() {
        return name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }

    public Object getProperty() {
        return property;
    }

    public Property setProperty(Object property) {
        this.property = property;
        return this;
    }

    public PolicyFun getPolicyFun() {
        return policyFun;

    }

    public Property setPolicyFun(PolicyFun policyFun) {
        this.policyFun = policyFun;
        return this;
    }

    public boolean isJudge() {
        return judge;
    }

    public Property setJudge(boolean judge) {
        this.judge = judge;
        return this;
    }
}
