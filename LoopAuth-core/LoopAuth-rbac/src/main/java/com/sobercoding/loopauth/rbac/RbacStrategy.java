package com.sobercoding.loopauth.rbac;


import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.function.MeFunction;
import com.sobercoding.loopauth.rbac.carryout.LoopAuthPermission;
import com.sobercoding.loopauth.rbac.face.PermissionInterface;
import com.sobercoding.loopauth.rbac.face.PermissionInterfaceDefImpl;
import java.util.function.Supplier;

/**
 * LoopAuth  Bean管理器
 * @author: Sober
 */

public class RbacStrategy {


    /**
     * 验证权限策略
     */
    private volatile static LoopAuthPermission loopAuthPermission;

    public static void setLoopAuthPermission(LoopAuthPermission loopAuthPermission) {
        RbacStrategy.loopAuthPermission = loopAuthPermission;
    }

    public static LoopAuthPermission getLoopAuthPermission() {
        if (RbacStrategy.loopAuthPermission == null){
            synchronized(RbacStrategy.class){
                if (RbacStrategy.loopAuthPermission == null){
                    setLoopAuthPermission(new LoopAuthPermission());
                }
            }
        }
        return RbacStrategy.loopAuthPermission;
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

    /**
     * 获取loginId
     */
    public static Supplier<String> getLoginId = () -> {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED, "RbacStrategy.getLoginId");
    };



}
