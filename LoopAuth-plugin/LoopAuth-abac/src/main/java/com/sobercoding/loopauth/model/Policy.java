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

    /**属性开始**/

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
    private Map<String,Property> customPro = new HashMap<>();

    /**属性结束**/

}
