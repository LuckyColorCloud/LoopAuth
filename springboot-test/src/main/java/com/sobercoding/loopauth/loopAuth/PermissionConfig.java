package com.sobercoding.loopauth.loopAuth;

import com.sobercoding.loopauth.permission.PermissionInterface;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限 角色  配置
 * @author Yun
 */
@Component
public class PermissionConfig implements PermissionInterface {
    @Override
    public Set<String> getPermissionSet(String userId, String loginType) {
        //这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user-*");
            }
        };
    }

    @Override
    public Set<String> getRoleSet(String userId, String loginType) {
        //这里只做演示 所以写死 根据业务查询数据库或者其他操作
        return new HashSet<String>() {
            {
                add("user");
            }
        };
    }
}
