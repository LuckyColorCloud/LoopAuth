package com.sobercoding.loopauth.abac.policy.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ABAC策略模型
 * @author Sober
 */
public class Policy implements Serializable,Comparable<Policy> {

    private static final long serialVersionUID = 1L;

    /**
     * 策略名称 默认空串
     */
    private String name = "";

    /**
     * 属性列表
     */
    private Map<String, Map<String, String>> propertyMap = new HashMap<>();


    /**
     * 写入Action属性
     * @author Sober
     * @param key 属性名
     * @param property 属性规则值
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setActionProperty(String key, String property){
        if (propertyMap.get(PropertyEnum.ACTION.toString()) == null) {
            propertyMap.put(PropertyEnum.ACTION.toString(), new HashMap<String, String>());
            propertyMap.get(PropertyEnum.ACTION.toString()).put(key, property);
        } else {
            propertyMap.get(PropertyEnum.ACTION.toString()).put(key, property);
        }
        return this;
    }

    /**
     * 写入Contextual属性
     * @author Sober
     * @param key 属性名
     * @param property 属性规则值
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setContextualProperty(String key, String property){
        if (propertyMap.get(PropertyEnum.CONTEXTUAL.toString()) == null) {
            propertyMap.put(PropertyEnum.CONTEXTUAL.toString(), new HashMap<String, String>());
            propertyMap.get(PropertyEnum.CONTEXTUAL.toString()).put(key, property);
        } else {
            propertyMap.get(PropertyEnum.CONTEXTUAL.toString()).put(key, property);
        }
        return this;
    }

    /**
     * 写入ResObject属性
     * @author Sober
     * @param key 属性名
     * @param property 属性规则值
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setResObjectProperty(String key, String property){
        if (propertyMap.get(PropertyEnum.RES_OBJECT.toString()) == null) {
            propertyMap.put(PropertyEnum.RES_OBJECT.toString(), new HashMap<String, String>());
            propertyMap.get(PropertyEnum.RES_OBJECT.toString()).put(key, property);
        } else {
            propertyMap.get(PropertyEnum.RES_OBJECT.toString()).put(key, property);
        }
        return this;
    }

    /**
     * 写入Subject属性
     * @author Sober
     * @param key 属性名
     * @param property 属性规则值
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setSubjectProperty(String key, String property){
        if (propertyMap.get(PropertyEnum.SUBJECT.toString()) == null) {
            propertyMap.put(PropertyEnum.SUBJECT.toString(), new HashMap<String, String>());
            propertyMap.get(PropertyEnum.SUBJECT.toString()).put(key, property);
        } else {
            propertyMap.get(PropertyEnum.SUBJECT.toString()).put(key, property);
        }
        return this;
    }

    public Map<String, Map<String, String>> getPropertyMap() {
        return propertyMap;
    }


    public String getName() {
        return name;
    }

    public Policy setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Policy policy = (Policy) obj;

        return Objects.equals(this.name, policy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, propertyMap);
    }
    @Override
    public int compareTo(Policy obj) {
        return Integer.compare(this.hashCode() - obj.hashCode(), 0);
    }
}
