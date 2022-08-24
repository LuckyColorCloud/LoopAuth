package com.sobercoding.loopauth.permission;

import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

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
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED,"PermissionInterface.getPermissionSet()");
    }

    @Override
    public Set<String> getRoleSet(String userId, String loginType) {
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED, "PermissionInterface.getRoleSet()");
    }
}
