package com.sobercoding.loopauth.rbac;


import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.context.LoopAuthContextDefaultImpl;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.dao.LoopAuthDaoImpl;
import com.sobercoding.loopauth.face.component.LoopAuthLogin;
import com.sobercoding.loopauth.face.component.LoopAuthPermission;
import com.sobercoding.loopauth.face.component.LoopAuthToken;
import com.sobercoding.loopauth.function.LrFunction;
import com.sobercoding.loopauth.function.PolicyFun;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.permission.PermissionInterface;
import com.sobercoding.loopauth.permission.PermissionInterfaceDefImpl;
import com.sobercoding.loopauth.rbac.face.PermissionInterface;
import com.sobercoding.loopauth.rbac.face.PermissionInterfaceDefImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class RbacStrategy {


    /**
     * 验证权限策略
     */
    private volatile static LoopAuthRbac loopAuthRbac;

    public static void setLoopAuthPermission(LoopAuthRbac loopAuthPermission) {
        RbacStrategy.loopAuthRbac = loopAuthPermission;
    }

    public static LoopAuthRbac getLoopAuthPermission() {
        if (RbacStrategy.loopAuthRbac == null){
            synchronized(RbacStrategy.class){
                if (RbacStrategy.loopAuthRbac == null){
                    setLoopAuthPermission(new LoopAuthRbac());
                }
            }
        }
        return RbacStrategy.loopAuthRbac;
    }


    /**
     * 权限认证 Bean 获取角色/权限代码
     */
    private volatile static PermissionInterface permissionInterface;
    public static void setPermissionInterface(PermissionInterface permissionInterface) {
        RbacStrategy.permissionInterface = permissionInterface;
    }

    public static PermissionInterface getPermissionInterface() {
        if (permissionInterface == null) {
            synchronized (RbacStrategy.class) {
                if (permissionInterface == null) {
                    setPermissionInterface(new PermissionInterfaceDefImpl());
                }
            }
        }
        return permissionInterface;
    }



}
