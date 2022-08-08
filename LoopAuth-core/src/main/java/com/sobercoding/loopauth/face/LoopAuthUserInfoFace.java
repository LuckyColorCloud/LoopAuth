package com.sobercoding.loopauth.face;

import java.util.Collection;
import java.util.Set;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description: 获取用户信息接口
 * @create: 2022/08/08 16:45
 */
public interface LoopAuthUserInfoFace {

    /**
     * @Method: getPermissionCodes
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取用户角色权限码
     * @param userId
     * @Return: Collection<String>
     * @Exception:
     * @Date: 2022/8/8 16:55
     */
    public Collection<String> getPermissionCodes(String userId);

    /**
     * @Method: getRoles
     * @Author: Sober
     * @Version: 0.0.1
     * @Description: 获取用户角色列表
     * @param userId
     * @Return: Collection<String>
     * @Exception:
     * @Date: 2022/8/8 16:55
     */
    public Collection<String> getRoles(String userId);
}
