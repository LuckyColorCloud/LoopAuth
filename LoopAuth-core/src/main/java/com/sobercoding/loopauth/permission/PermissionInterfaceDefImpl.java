package com.sobercoding.loopauth.permission;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取权限 角色集合 默认实现
 *
 * @author Yun
 */
public class PermissionInterfaceDefImpl implements PermissionInterface {
    @Override
    public Set<String> getPermissionSet(String userId, String loginType) {
        return new HashSet<>();
    }

    @Override
    public Set<String> getRoleSet(String userId, String loginType) {
        return new HashSet<>();
    }
}
