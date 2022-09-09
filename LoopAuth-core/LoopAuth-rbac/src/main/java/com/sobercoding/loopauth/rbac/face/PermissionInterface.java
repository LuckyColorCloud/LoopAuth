package com.sobercoding.loopauth.rbac.face;

import java.util.Set;

/**
 * @author Yun
 */
public interface PermissionInterface {

    /**
     *  获取一个用户的 权限集合
     * @param userId 用户id
     * @param loginType 用户登入类型
     * @return 去重后的集合
     */
     Set<String> getPermissionSet(String userId,String loginType);

    /**
     *  获取一个用户的 角色集合
     * @param userId 用户id
     * @param loginType 用户登入类型
     * @return 去重后的集合
     */
    Set<String> getRoleSet(String userId,String loginType);
}
