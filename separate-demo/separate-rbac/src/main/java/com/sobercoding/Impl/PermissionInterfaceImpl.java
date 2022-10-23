package com.sobercoding.Impl;

import com.sobercoding.loopauth.rbac.face.PermissionInterface;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sober
 */
public class PermissionInterfaceImpl implements PermissionInterface {

    @Override
    public Set<String> getPermissionSet(String userId, String loginType) {
        // 这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user-*");
            }
        };
    }

    @Override
    public Set<String> getRoleSet(String userId, String loginType) {
        // 这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user");
            }
        };
    }
}
