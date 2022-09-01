package com.sobercoding.loopauth.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ABAC策略模型
 * @author Sober
 */
public class Policy {

    /**
     * 策略名称 默认空串
     */
    private String name;

    /**
     * 策略版本 默认空串
     */
    private String version = "";

    /**
     * 优先级，数字小的先执行
     */
    private int ranking = 0;

    /**
     * 路由/权限代码组 默认*全匹配
     */
    private String apiGroup = "*";

    /**
     * 用户id 默认空 即忽略此属性
     */
    private Set<String> loginIds;

    /**
     * 用户角色 默认空 即忽略此属性
     */
    private Set<String> roles;

    /**
     * 权限代码 默认空 即忽略此属性
     */
    private Set<String> permissions;

    /**
     * 属性列表
     */
    private Map<String,Property> propertyMap = new HashMap<>();


    /**
     * 写入属性
     * @author Sober
     * @param property 属性类
     * @return com.sobercoding.loopauth.model.Policy
     */
    public Policy setProperty(Property property){
        propertyMap.put(property.getName(),property);
        return this;
    }


    public String getName() {
        return name;
    }

    public Policy setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Policy setVersion(String version) {
        this.version = version;
        return this;
    }

    public int getRanking() {
        return ranking;
    }

    public Policy setRanking(int ranking) {
        this.ranking = ranking;
        return this;
    }

    public String getApiGroup() {
        return apiGroup;
    }

    public Policy setApiGroup(String apiGroup) {
        this.apiGroup = apiGroup;
        return this;
    }

    public Set<String> getLoginIds() {
        return loginIds;
    }

    public Policy setLoginIds(Set<String> loginIds) {
        this.loginIds = loginIds;
        return this;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Policy setRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Policy setPermissions(Set<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Map<String, Property> getPropertyMap() {
        return propertyMap;
    }

    public Policy setPropertyMap(Map<String, Property> propertyMap) {
        this.propertyMap = propertyMap;
        return this;
    }
}
