package com.sobercoding.loopauth.rbac.carryout;

import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.rbac.RbacStrategy;

/**
 * @author Sober
 */
public class LoopAuthRbac {

    public static LoopAuthPermission LOOP_AUTH_PERMISSION = RbacStrategy.getLoopAuthPermission();

    /**
     * 角色认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param roles 角色列表
     */
    public static void checkByRole(LoopAuthVerifyMode loopAuthVerifyMode, String... roles) {
        LOOP_AUTH_PERMISSION.checkByRole(loopAuthVerifyMode,roles);
    }

    /**
     * 角色认证
     * @author: Sober
     * @param roles 角色列表
     */
    public static void checkByRole(String... roles) {
        // 默认AND
        LOOP_AUTH_PERMISSION.checkByRole(LoopAuthVerifyMode.AND,roles);
    }


    /**
     * 权限认证
     * @author: Sober
     * @param loopAuthVerifyMode 认证方式
     * @param permissions 权限代码列表
     */
    public static void checkByPermission(LoopAuthVerifyMode loopAuthVerifyMode, String... permissions) {
        LOOP_AUTH_PERMISSION.checkByPermission(loopAuthVerifyMode,permissions);
    }

    /**
     * 权限认证
     * @author: Sober
     * @param permissions 权限代码列表
     */
    public static void checkByPermission(String... permissions) {
        // 默认AND
        LOOP_AUTH_PERMISSION.checkByPermission(LoopAuthVerifyMode.AND,permissions);
    }

}
