package com.sobercoding.loopauth.abac.model;

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
    private String name;

    /**
     * 路由组 默认*全匹配
     */
    private String apiGroup = "";

    /**
     * 权限代码组 默认*全匹配
     */
    private String permissionGroup = "";

    /**
     * 属性列表
     */
    private Map<String, Object> propertyMap = new HashMap<>();


    /**
     * 写入属性
     * @author Sober
     * @param key 属性名
     * @param property 属性规则值
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setProperty(String key, Object property){
        propertyMap.put(key, property);
        return this;
    }

    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }


    public String getName() {
        return name;
    }

    public Policy setName(String name) {
        this.name = name;
        return this;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public Policy setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
        return this;
    }

    public String getApiGroup() {
        return apiGroup;
    }

    public Policy setApiGroup(String apiGroup) {
        this.apiGroup = apiGroup;
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
        return Objects.hash(name);
    }
    @Override
    public int compareTo(Policy obj) {
        return Integer.compare(this.hashCode() - obj.hashCode(), 0);
    }
}